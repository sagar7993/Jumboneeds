package com.jumboneeds.services;

import com.jumboneeds.entities.*;
import com.jumboneeds.utils.AnalyticsTracking;
import com.jumboneeds.utils.Constants;
import com.jumboneeds.utils.DateOperations;
import com.mixpanel.mixpanelapi.MessageBuilder;
import com.mixpanel.mixpanelapi.MixpanelAPI;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
@Transactional(rollbackFor = {Exception.class})
public class BillingJobService {
    private static final String TAG = "BillingJobService : ";

    @Autowired
    private BillingLogService billingLogService;

    @Autowired
    private BillingLogTypeService billingLogTypeService;

    @Autowired
    private FilterSubscriptionService filterSubscriptionService;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private BillingMasterService billingMasterService;

    @Autowired
    private JobStatusService jobStatusService;

    @Autowired
    private MailService mailService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    private JobStatus jobStatus;

    @Scheduled(cron = "0 30 4 * * ?")
    public void runBillingJob() {
        Date date = DateOperations.getTodayStartDate();

        jobStatus = new JobStatus();
        jobStatus.setDate(date);
        jobStatus.setJobName("Billing Job");
        jobStatus.setStatus(false);
        jobStatus.setMessage("Billing Job Started");

        try {
            jobStatusService.saveJob(jobStatus);
        } catch (Exception e){
            errorLogger.error(TAG + "Error in creating Billing JobStatus on date : " + date);

            sendBillingJobErrorMail("Billing Job Failed on : " + date, "Error in creating Billing JobStatus", date);
            return;
        }

        List<Subscription> filteredSubscriptions = filterSubscriptionService.fetchFilteredSubscriptions(null, date, true);

        populateUserSubscriptionMap(filteredSubscriptions, date);
    }

    public void runBillingJob(Long dateInMillis) {
        Date date = DateOperations.getClientToServerCustomStartDateFromLong(dateInMillis, 0);

        jobStatus = new JobStatus();
        jobStatus.setDate(date);
        jobStatus.setJobName("Billing Job");
        jobStatus.setStatus(false);
        jobStatus.setMessage("Billing Job Started");

        try {
            jobStatusService.saveJob(jobStatus);
        } catch (Exception e){
            errorLogger.error(TAG + "Error in creating Billing JobStatus on date : " + date);

            sendBillingJobErrorMail("Billing Job Failed on : " + date, "Error in creating Billing JobStatus", date);
            return;
        }

        List<Subscription> filteredSubscriptions = filterSubscriptionService.fetchFilteredSubscriptions(null, date, true);

        populateUserSubscriptionMap(filteredSubscriptions, date);
    }

    private void populateUserSubscriptionMap(List<Subscription> filteredSubscriptions, Date date){
        Map<String, List<Subscription>> userSubscriptionMap = new HashMap<>();

        for (Subscription subscription : filteredSubscriptions) {
            String key = subscription.getUser().getId();

            if (userSubscriptionMap.containsKey(key)){
                userSubscriptionMap.get(key).add(subscription);
            } else {
                List<Subscription> subscriptions = new ArrayList<>();
                subscriptions.add(subscription);
                userSubscriptionMap.put(key, subscriptions);
            }
        }

        createPerSubscriptionDeductionBillingLogs(userSubscriptionMap, date);
    }

    private  Map<String, BillingMaster> populateBillingMasterMap(){
        Map<String, BillingMaster> billingMasterMap = new HashMap<>();

        List<BillingMaster> billingMasters = billingMasterService.fetchCurrentBillingMasters();

        for (BillingMaster billingMaster : billingMasters) {
            billingMasterMap.put(billingMaster.getUser().getId(), billingMaster);
        }

        return billingMasterMap;
    }

    private void createPerSubscriptionDeductionBillingLogs(Map<String, List<Subscription>> userSubscriptionMap, Date date){
        BillingLogType retrievedTotalDeductionType = billingLogTypeService.findByType(1);

        BillingLogType retrievedPerProductDeductionType  = billingLogTypeService.findByType(2);

        PaymentType retrievedBillingJobPaymentType  = paymentTypeService.findByType(6);

        Map<String, BillingMaster> billingMasterMap = populateBillingMasterMap();

        List<BillingLog> totalDeductionBillingLogs = new ArrayList<>();

        for (String key : userSubscriptionMap.keySet()) {
            List<Subscription> subscriptions = userSubscriptionMap.get(key);

            BillingMaster billingMaster = billingMasterMap.get(key);

            double jumboNeedsAmount = 0.0;
            double totalAmount = 0.0;

            //Create per Subscription Deduction BillingLogs
            for (Subscription subscription : subscriptions) {
                BillingLog billingLog = new BillingLog();
                billingLog.setDate(date);

                if (subscription.getSubscriptionType().getType() == 4){
                    billingLog.setAmount(subscription.getProductUnitPrice() * subscription.getTransientProductQuantity());
                } else {
                    billingLog.setAmount(subscription.getProduct().getProductUnitPrice() * subscription.getTransientProductQuantity());
                }

                //Update total amount
                if (!subscription.getProduct().getFulfilledByPartner()){
                    jumboNeedsAmount += billingLog.getAmount();
                }

                //Update total amount
                totalAmount += billingLog.getAmount();

                String description = new StringBuilder().append("Rs.").append(billingLog.getAmount()).append(" deducted for ")
                        .append(subscription.getTransientProductQuantity()).append(" X (").append(subscription.getProduct().getProductMaster().getProductUnitSize())
                        .append(") of ").append(subscription.getProduct().getProductMaster().getProductName()).toString();
                billingLog.setDescription(description);

                billingLog.setFlat(subscription.getUser().getFlat());
                billingLog.setBillingLogType(retrievedPerProductDeductionType);
                billingLog.setPaymentType(retrievedBillingJobPaymentType);
                billingLog.setUser(subscription.getUser());
                billingLog.setSubscription(subscription);

                try {
                    //Save per Subscription Deduction BillingLogs
                    billingLogService.save(billingLog);
                } catch (Exception e){
                    String body = "Error in creating per Subscription Deduction BillingLog on date : " + date + " for Subscription : " + subscription.getId();
                    errorLogger.error(TAG + body);

                    sendBillingJobErrorMail("Billing Job Failed on : " + date, body, date);

                    throw e;
                }
            }

            //Create Total Deduction BillingLogs
            BillingLog billingLog = new BillingLog();
            billingLog.setDate(date);
            billingLog.setAmount(totalAmount);
            billingLog.setJumboNeedsAmount(jumboNeedsAmount);
            billingLog.setPreviousBalance(billingMaster.getAmount());

            String description = new StringBuilder().append("Rs.").append(billingLog.getAmount()).append(" deducted from Rs.")
                    .append(billingMaster.getAmount()).toString();
            billingLog.setDescription(description);

            billingLog.setFlat(billingMaster.getUser().getFlat());
            billingLog.setBillingLogType(retrievedTotalDeductionType);
            billingLog.setPaymentType(retrievedBillingJobPaymentType);
            billingLog.setUser(billingMaster.getUser());

            totalDeductionBillingLogs.add(billingLog);

            try {
                //Save Total Deduction BillingLogs
                billingLogService.save(billingLog);
            } catch (Exception e){
                String body = "Error in creating Total Deduction BillingLog on date : " + date + " for User : " + billingMaster.getUser().getId();
                errorLogger.error(TAG + body);

                sendBillingJobErrorMail("Billing Job Failed on : " + date, body, date);

                throw e;
            }

            try {
                //Update BillingMaster
                billingMaster.setAmount(billingMaster.getAmount() - billingLog.getAmount());
                billingMasterService.save(billingMaster);
            } catch (Exception e){
                String body = "Error in updating BillingMaster : " + billingMaster.getId() + " during Billing Job on " + date;
                errorLogger.error(TAG + body);

                sendBillingJobErrorMail("Billing Job Failed on : " + date, body, date);

                throw e;
            }
        }

        sendBillingJobSuccessMail(userSubscriptionMap.size(), date);

        updateMixpanelUsers(totalDeductionBillingLogs);
    }

    private void updateMixpanelUsers(List<BillingLog> billingLogs){
        MessageBuilder messageBuilder = new MessageBuilder(Constants.MIX_PANEL_API_TOKEN);

        for (BillingLog billingLog : billingLogs) {
            Map<String, Long> properties = new HashMap<>();

            if (billingLog.getJumboNeedsAmount() > 0.0){
                properties.put(AnalyticsTracking.JUMBO_NEEDS_AMOUNT_SPENT, billingLog.getJumboNeedsAmount().longValue());
            }

            properties.put(AnalyticsTracking.TOTAL_AMOUNT_SPENT, billingLog.getAmount().longValue());

            JSONObject update = messageBuilder.increment(billingLog.getUser().getId(), properties);

            MixpanelAPI mixpanelAPI = new MixpanelAPI();

            try {
                mixpanelAPI.sendMessage(update);
            } catch (IOException e) {
                debugLogger.debug(TAG + "Mixpanel user amount update failed for User : " + billingLog.getUser().getId());

                e.printStackTrace();
            }
        }
    }

    private void sendBillingJobErrorMail(String subject, String htmlText, Date date) {
        String[] toArray = new String[]{Constants.TECH_EMAIL};
        String from = Constants.FROM_EMAIL;
        String replyTo = Constants.REPLY_TO_EMAIL;

        jobStatus.setDate(date);
        jobStatus.setJobName("Billing Job");
        jobStatus.setStatus(false);
        jobStatus.setMessage("Billing Job Failed");

        try {
            jobStatusService.saveJob(jobStatus);
        } catch (Exception e){
            errorLogger.error(TAG + "Error in creating Billing JobStatus fot date : " + date);
        }

        mailService.buildEmail(toArray, from, replyTo, subject, htmlText, null, null);
    }

    private void sendBillingJobSuccessMail(int userCount, Date date) {
        String[] toArray = new String[]{Constants.TECH_EMAIL};
        String from = Constants.FROM_EMAIL;
        String replyTo = Constants.REPLY_TO_EMAIL;

        String subject = "Billing Job Successful on " + date;

        String htmlText = "<html><body>"
                + "<h3>Billing Job for <b>" + date + "</b> has run successfully.</h3>"
                + "<h2><b>" + userCount + "</b> entries in BillingMaster have been updated.</h2>"
                + "</body></html>";

        jobStatus.setDate(date);
        jobStatus.setJobName("Billing Job");
        jobStatus.setStatus(true);
        jobStatus.setMessage("Billing Job Succeeded");

        try {
            jobStatusService.saveJob(jobStatus);
        } catch (Exception e){
            errorLogger.error(TAG + "Error in creating Billing JobStatus fot date : " + date);
        }

        mailService.buildEmail(toArray, from, replyTo, subject, htmlText, null, null);
    }
}