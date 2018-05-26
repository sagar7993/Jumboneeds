package com.jumboneeds.services;

import com.jumboneeds.entities.*;
import com.jumboneeds.utils.Constants;
import com.jumboneeds.utils.DateOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(rollbackFor = {Exception.class})
public class BillRegulatorJobService {
    private static final String TAG = "BillRegulatorJobService : ";

    @Autowired
    private UserService userService;

    @Autowired
    private BillingMasterService billingMasterService;

    @Autowired
    private BillingLogService billingLogService;

    @Autowired
    private SubscriptionExceptionService subscriptionExceptionService;

    @Autowired
    private BillingLogTypeService billingLogTypeService;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private SubscriptionExceptionTypeService subscriptionExceptionTypeService;

    @Autowired
    private FilterSubscriptionService filterSubscriptionService;

    @Autowired
    private JobStatusService jobStatusService;

    @Autowired
    private MailService mailService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    private JobStatus jobStatus;

//    @Scheduled(cron = "40 17 15 * * ?")
    public void runBillRegulatorJob() {
        Date date = DateOperations.getTodayStartDate();

        jobStatus = new JobStatus();
        jobStatus.setDate(date);
        jobStatus.setJobName("Bill Regulator Job");
        jobStatus.setStatus(false);
        jobStatus.setMessage("Bill Regulator Job Started");

        try {
            jobStatusService.saveJob(jobStatus);
        } catch (Exception e){
            errorLogger.error(TAG + "Error in creating Bill Regulator JobStatus on date : " + date);

            sendBillRegulatorJobErrorMail("Bill Regulator Job Failed on : " + date, "Error in creating Bill Regulator JobStatus", date);
            return;
        }

        List<User> users = userService.findUsersWithBalanceBelowMinimumBuildingAmount();

        List<Subscription> subscriptions = filterSubscriptionService.fetchFilteredSubscriptionsForUserList(users, date);

        populateUserSubscriptionMap(subscriptions, date);
    }

    public void runBillRegulatorJob(Long dateInMillis) {
        Date date = DateOperations.getClientToServerCustomStartDateFromLong(dateInMillis, 0);

        jobStatus = new JobStatus();
        jobStatus.setDate(date);
        jobStatus.setJobName("Bill Regulator Job");
        jobStatus.setStatus(false);
        jobStatus.setMessage("Bill Regulator Job Started");

        try {
            jobStatusService.saveJob(jobStatus);
        } catch (Exception e){
            errorLogger.error(TAG + "Error in creating Bill Regulator JobStatus on date : " + date);

            sendBillRegulatorJobErrorMail("Bill Regulator Job Failed on : " + date, "Error in creating Bill Regulator JobStatus", date);
            return;
        }

        List<User> users = userService.findUsersWithBalanceBelowMinimumBuildingAmount();

        List<Subscription> filteredSubscriptions = filterSubscriptionService.fetchFilteredSubscriptionsForUserList(users, date);

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

        createBillingLogs(userSubscriptionMap, date);
    }

    private void createBillingLogs(Map<String, List<Subscription>> userSubscriptionMap, Date date){
        BillingLogType retrievedBillingLogType = billingLogTypeService.findByType(4);

        PaymentType retrievedPaymentType  = paymentTypeService.findByType(7);

        SubscriptionExceptionType retrievedSubscriptionExceptionType  = subscriptionExceptionTypeService.findByType(1);

        for (String key : userSubscriptionMap.keySet()) {
            User user = userService.findById(key);
            List<Subscription> subscriptions = userSubscriptionMap.get(key);
            BillingLog billingLog = new BillingLog();
            billingLog.setAmount(0.0);
            billingLog.setCashback(0.0);
            billingLog.setDate(date);
            billingLog.setDescription(Constants.SERVICE_DISCONTINUED);
            billingLog.setFlat(user.getFlat());
            billingLog.setPreviousBalance(billingMasterService.getAmountForBillingMaster(user.getBillingMasterId()));
            billingLog.setBillingLogType(retrievedBillingLogType);
            billingLog.setPaymentType(retrievedPaymentType);
            billingLog.setUser(user);

            try {
                billingLogService.save(billingLog);
            } catch (Exception e){
                errorLogger.error(TAG + "Error in creating BillingLog for User : " + user.getId());

                throw e;
            }

            for (Subscription subscription : subscriptions) {
                SubscriptionException subscriptionException = new SubscriptionException();
                subscriptionException.setDate(date);
                subscriptionException.setProductQuantity(0);
                subscriptionException.setSubscription(subscription);
                subscriptionException.setSubscriptionExceptionType(retrievedSubscriptionExceptionType);
                subscriptionException.setUser(user);

                try {
                    subscriptionExceptionService.save(subscriptionException);
                } catch (Exception e){
                    errorLogger.error(TAG + "Error in creating SubscriptionException for Subscription : " + subscription.getId());

                    throw  e;
                }
            }
        }

        sendBillRegulatorJobSuccessMail(userSubscriptionMap.size(), date);
    }

    private void sendBillRegulatorJobErrorMail(String subject, String htmlText, Date date) {
        String[] toArray = new String[]{Constants.TECH_EMAIL};
        String from = Constants.FROM_EMAIL;
        String replyTo = Constants.REPLY_TO_EMAIL;

        jobStatus.setDate(date);
        jobStatus.setJobName("Bill Regulator Job");
        jobStatus.setStatus(false);
        jobStatus.setMessage("Bill Regulator Job Failed");

        try {
            jobStatusService.saveJob(jobStatus);
        } catch (Exception e){
            errorLogger.error(TAG + "Error in creating Bill Regulator JobStatus fot date : " + date);
        }

        mailService.buildEmail(toArray, from, replyTo, subject, htmlText, null, null);
    }

    private void sendBillRegulatorJobSuccessMail(int userCount, Date date) {
        String[] toArray = new String[]{Constants.TECH_EMAIL};
        String from = Constants.FROM_EMAIL;
        String replyTo = Constants.REPLY_TO_EMAIL;

        String subject = "Bill Regulator Job Successful on " + date;

        String htmlText = "<html><body>"
                + "<h3>Bill Regulator Job for <b>" + DateOperations.getTodayStartDate() + "</b> has run successfully.</h3>"
                + "<h2><b>" + userCount + "</b> entries in BillingMaster have been updated.</h2>"
                + "</body></html>";

        jobStatus.setDate(DateOperations.getTodayStartDate());
        jobStatus.setJobName("Bill Regulator Job");
        jobStatus.setStatus(true);
        jobStatus.setMessage("Bill Regulator Job Succeeded");

        try {
            jobStatusService.saveJob(jobStatus);
        } catch (Exception e){
            errorLogger.error(TAG + "Error in creating Bill Regulator JobStatus fot date : " + date);
        }

        mailService.buildEmail(toArray, from, replyTo, subject, htmlText, null, null);
    }

}