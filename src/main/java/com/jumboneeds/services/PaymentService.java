package com.jumboneeds.services;

import com.jumboneeds.beans.IdBean;
import com.jumboneeds.beans.NotificationBean;
import com.jumboneeds.beans.PaymentBean;
import com.jumboneeds.beans.StatusBean;
import com.jumboneeds.entities.*;
import com.jumboneeds.utils.AnalyticsTracking;
import com.jumboneeds.utils.Constants;
import com.jumboneeds.utils.DateOperations;
import com.jumboneeds.utils.NotificationManager;
import com.mixpanel.mixpanelapi.MessageBuilder;
import com.mixpanel.mixpanelapi.MixpanelAPI;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

@Service
public class PaymentService {
    private static final String TAG = "PaymentService : ";

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private BillingMasterService billingMasterService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private ExecutiveService executiveService;

    @Autowired
    private BillingLogService billingLogService;

    @Autowired
    private BillingLogTypeService billingLogTypeService;

    @Autowired
    private CashPickUpRequestService cashPickUpRequestService;

    @Autowired
    private MailService mailService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public StatusBean makePickupRequest(IdBean idBean){
        StatusBean statusBean = new StatusBean();

        User retrievedUser = userService.findById(idBean.getId());

        if(retrievedUser == null){
            debugLogger.debug(TAG + "No User found for Id : " + idBean.getId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }

        CashPickUpRequest cashPickupRequest = new CashPickUpRequest();
        cashPickupRequest.setDate(DateOperations.getTodayStartDate());
        cashPickupRequest.setUser(retrievedUser);

        try {
            cashPickUpRequestService.save(cashPickupRequest);
        } catch (Exception e){
            errorLogger.error(TAG + "Error in creating CashPickupRequest for User : " + retrievedUser.getId());
        }

        String block = retrievedUser.getBlock() != null ? retrievedUser.getBlock().getBlockName() : "";

        Double currentBalance = billingMasterService.getAmountForBillingMaster(retrievedUser.getBillingMasterId());

        StringBuilder bodyString = new StringBuilder("Cash pickup requested by " + retrievedUser.getUserName() + " (" + retrievedUser.getMobileNumber() + ") " + " from "
                + retrievedUser.getBuilding().getBuildingName() + " - " + block + retrievedUser.getFlat() + ". Current balance is Rs." + currentBalance);

        try {
            mailService.buildEmail(new String[]{Constants.FROM_EMAIL}, Constants.FROM_EMAIL, Constants.REPLY_TO_EMAIL, "Cash Pickup", bodyString.toString(), null, null);
            statusBean.setStatus(1);
        } catch (Exception e){
            errorLogger.error(TAG + "Cash Pickup Request mail service failed for User : " + retrievedUser.getId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        }

        return statusBean;
    }

    public StatusBean rechargeWallet(PaymentBean paymentBean){
        StatusBean statusBean = new StatusBean();

        User retrievedUser = userService.findById(paymentBean.getUserId());

        if(retrievedUser == null){
            debugLogger.debug(TAG + "No User found for Mobile Number : " + paymentBean.getMobileNumber());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        } else {
            paymentBean.setUser(retrievedUser);
        }

        BillingMaster retrievedBillingMaster = billingMasterService.findById(retrievedUser.getBillingMasterId());

        if(retrievedBillingMaster == null){
            debugLogger.debug(TAG + "No BillingMaster found for Id : " + retrievedUser.getBillingMasterId());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        } else {
            paymentBean.setBillingMaster(retrievedBillingMaster);
        }

        return executePayment(paymentBean);
    }

    public StatusBean makePayment(PaymentBean paymentBean){
        StatusBean statusBean = new StatusBean();
        statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

        User retrievedUser = userService.findByMobileNumber(paymentBean.getMobileNumber());

        if(retrievedUser == null){
            debugLogger.debug(TAG + "No User found for Mobile Number : " + paymentBean.getMobileNumber());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        } else {
            paymentBean.setUser(retrievedUser);
        }

        BillingMaster retrievedBillingMaster = billingMasterService.findById(retrievedUser.getBillingMasterId());

        if(retrievedBillingMaster == null){
            debugLogger.debug(TAG + "No BillingMaster found for Id : " + retrievedUser.getBillingMasterId());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        } else {
            paymentBean.setBillingMaster(retrievedBillingMaster);
        }

        if (StringUtils.isEmpty(paymentBean.getPartnerId()) && StringUtils.isEmpty(paymentBean.getExecutiveId())){
            debugLogger.debug(TAG + "Partner/Executive payment tried without Partner/Executive Id");
            statusBean.setMessage(Constants.AUTHORIZATION_FAILED);
            return statusBean;
        } else {
            if (!StringUtils.isEmpty(paymentBean.getPartnerId())){
                Partner retrievedPartner = partnerService.findById(paymentBean.getPartnerId());

                if(retrievedPartner == null){
                    debugLogger.debug(TAG + "No Partner found for Id : " + paymentBean.getPartnerId());

                    //Logout Partner from Partner App
                    statusBean.setStatus(2);
                    statusBean.setMessage(Constants.AUTHORIZATION_FAILED);
                    return statusBean;
                } else {
                    paymentBean.setPartner(retrievedPartner);
                }
            } else {
                Executive retrievedExecutive = executiveService.findById(paymentBean.getExecutiveId());

                if(retrievedExecutive == null){
                    debugLogger.debug(TAG + "No Executive found for Id : " + paymentBean.getExecutiveId());

                    //Logout Partner from Executive App
                    statusBean.setStatus(2);
                    statusBean.setMessage(Constants.AUTHORIZATION_FAILED);
                    return statusBean;
                } else {
                    paymentBean.setExecutive(retrievedExecutive);
                }
            }
        }

        return executePayment(paymentBean);
    }

    @Transactional
    public StatusBean executePayment(PaymentBean paymentBean){
        StatusBean statusBean = new StatusBean();
        statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

        if(paymentBean.getAmount() == 0 && paymentBean.getCashback() == 0) {
            debugLogger.debug(TAG + "Amount and Cashback both cannot be zero for User Mobile Number :" + paymentBean.getMobileNumber());
            statusBean.setMessage(Constants.INVALID_AMOUNT);
            return statusBean;
        }

        if (paymentBean.getPaymentType() == 3){
            paymentBean.setBankTxnId(paymentBean.getDescription());
        }

        PaymentType paymentType = paymentTypeService.findByType(paymentBean.getPaymentType());

        String description = buildDescription(paymentType.getType(), paymentBean);

        //Create New BillingMaster
        BillingMaster newBillingMaster = createNewBillingMaster(paymentBean, paymentBean.getBillingMaster().getStartDate(), paymentType);

        if(paymentType.getType() == 3) {
            newBillingMaster.setBankTxnId(paymentBean.getBankTxnId());
        }

        //Create BillingLog
        BillingLog billingLog = createBillingLog(paymentBean, paymentType, description);

        //Update Old BillingMaster
        BillingMaster oldBillingMaster = updateOldBillingMaster(paymentBean);

        try {
            billingMasterService.save(oldBillingMaster);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in updating BillingMaster : " + paymentBean.getBillingMaster().getId());
            return statusBean;
        }

        newBillingMaster.setDescription(description);

        try {
            billingMasterService.save(newBillingMaster);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in creating BillingMaster for User : " + paymentBean.getUser().getId());
            return statusBean;
        }

        billingLog.setBillingMaster(newBillingMaster);

        try {
            billingLogService.save(billingLog);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in creating BillingLog for User : " + paymentBean.getUser().getId());
            return statusBean;
        }

        sendMixPanelNotification(paymentBean.getUser(), billingLog.getAmount(), description, oldBillingMaster.getAmount());

        statusBean.setStatus(1);
        statusBean.setMessage(Constants.RECHARGE_DONE);
        return statusBean;
    }

    private String buildDescription(int type, PaymentBean paymentBean){
        switch (type){
            case 1 :
                return "Cash collected worth Rs." + (paymentBean.getAmount().intValue() + paymentBean.getCashback().intValue());
            case 2 :
                return "Coupons refunded worth Rs." + (paymentBean.getAmount().intValue() + paymentBean.getCashback().intValue());
            case 3 :
                return "Online recharge of Rs." + (paymentBean.getAmount().intValue() + paymentBean.getCashback().intValue()) + " done";
            case 4 :
                return "Refund in case of non-delivery worth Rs." + (paymentBean.getAmount().intValue() + paymentBean.getCashback().intValue()) + " due to " + paymentBean.getDescription();
            case 5 :
                return "Bill adjusted for Rs." + (paymentBean.getAmount().intValue() + paymentBean.getCashback().intValue()) + " due to " + paymentBean.getDescription();
            default :
                return "";
        }
    }

    private BillingMaster createNewBillingMaster(PaymentBean paymentBean, Date startDate, PaymentType paymentType){
        BillingMaster billingMaster = new BillingMaster();
        billingMaster.setAmount(paymentBean.getAmount() + paymentBean.getCashback());
        billingMaster.setCashback(paymentBean.getCashback());
        billingMaster.setMilkAmount(paymentBean.getMilkAmount());
        billingMaster.setNonMilkAmount(paymentBean.getNonMilkAmount());
        billingMaster.setStartDate(startDate);
        billingMaster.setEndDate(DateOperations.getTodayStartDate());
        billingMaster.setPaymentDate(DateOperations.getTodayStartDate());
        billingMaster.setPaymentAmount(paymentBean.getAmount());
        billingMaster.setCurrent(false);
        billingMaster.setUser(paymentBean.getUser());
        billingMaster.setPaymentType(paymentType);
        billingMaster.setDescription(paymentBean.getDescription());
        billingMaster.setBankTxnId(paymentBean.getBankTxnId());
        billingMaster.setPartner(paymentBean.getPartner());
        billingMaster.setExecutive(paymentBean.getExecutive());
        return billingMaster;
    }

    private BillingLog createBillingLog(PaymentBean paymentBean, PaymentType paymentType, String description){
        BillingLog billingLog = new BillingLog();
        billingLog.setBillingLogType(billingLogTypeService.findByType(3));
        billingLog.setAmount(paymentBean.getAmount());
        billingLog.setCashback(paymentBean.getCashback());
        billingLog.setMilkAmount(paymentBean.getMilkAmount());
        billingLog.setNonMilkAmount(paymentBean.getNonMilkAmount());
        billingLog.setDate(DateOperations.getTodayStartDate());
        billingLog.setDescription(description);
        billingLog.setFlat(paymentBean.getUser().getFlat());
        billingLog.setPreviousBalance(paymentBean.getBillingMaster().getAmount());
        billingLog.setPaymentType(paymentType);
        billingLog.setUser(paymentBean.getUser());
        return billingLog;
    }

    private BillingMaster updateOldBillingMaster(PaymentBean paymentBean){
        paymentBean.getBillingMaster().setAmount(paymentBean.getBillingMaster().getAmount() + paymentBean.getAmount() + paymentBean.getCashback());
        paymentBean.getBillingMaster().setCashback(paymentBean.getCashback());
        paymentBean.getBillingMaster().setStartDate(DateOperations.getTodayStartDate());
        paymentBean.getBillingMaster().setPaymentAmount(paymentBean.getAmount());
        paymentBean.getBillingMaster().setPaymentDate(DateOperations.getTodayStartDate());
        return paymentBean.getBillingMaster();
    }

    private void sendMixPanelNotification(User user, Double amount, String description, Double updatedBalance) {
        MessageBuilder messageBuilder = new MessageBuilder(Constants.MIX_PANEL_API_TOKEN);

        description += ". Your updated wallet balance is Rs." + updatedBalance.intValue() + ".";

        MixpanelAPI mixpanelAPI = new MixpanelAPI();

        Map<String, Long> properties = new HashMap<>();
        properties.put(AnalyticsTracking.TOTAL_AMOUNT_RECHARGED, amount.longValue());

        JSONObject update = messageBuilder.increment(user.getId(), properties);

        try {
            mixpanelAPI.sendMessage(update);
        } catch (IOException e) {
            debugLogger.debug(TAG + "Mixpanel user amount recharge failed for User : " + user.getId());
            e.printStackTrace();
        }

        if(!StringUtils.isEmpty(user.getFcmAndroidToken())) {
            NotificationBean notificationBean = new NotificationBean();
            List<NotificationBean> notificationBeans = new ArrayList<>();
            notificationBean.setTitle("Wallet recharge done");
            notificationBean.setShortDescription("Recharge of Rs." + amount.intValue() + " is done.");
            notificationBean.setLongDescription(description);
            //notificationBean.setNotificationImageUrl(null);
            notificationBean.setNotificationType(2);
            notificationBean.setReceiverId(user.getFcmAndroidToken());
            notificationBeans.add(notificationBean);

            NotificationManager.sendNotification(notificationBeans);
        } else {
            debugLogger.debug(TAG + "FCM Id does not exist for User : " + user.getId());
        }

    }

}