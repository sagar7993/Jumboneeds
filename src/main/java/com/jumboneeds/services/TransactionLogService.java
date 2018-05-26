package com.jumboneeds.services;

import com.jumboneeds.beans.*;
import com.jumboneeds.entities.*;
import com.jumboneeds.utils.Constants;
import com.jumboneeds.utils.DateOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class TransactionLogService {
    private static final String TAG = "TransactionLogService : ";

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private UserService userService;

    @Autowired
    private BillingMasterService billingMasterService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public TransactionLogMaster fetchTransactionLogs(PartnerLogin partnerLogin, boolean isPartner) {
        String buildingName = "JumboNeeds";

        Partner retrievedPartner = partnerService.partnerWebLogin(partnerLogin.getUserName(), partnerLogin.getPassword());

        Date date = DateOperations.getCustomStartDateFromString(partnerLogin.getDateString());

        List<Building> buildings = new ArrayList<>();

        if(StringUtils.isEmpty(partnerLogin.getUserName()) && StringUtils.isEmpty(partnerLogin.getPassword())) {
            buildings = buildingService.fetchAll();
        } else {
            if (retrievedPartner == null) {
                debugLogger.debug(TAG + "No Partner found for UserName : " + partnerLogin.getUserName() + " & Password : " + partnerLogin.getPassword());
                return new TransactionLogMaster(null, null, null, null, null, null, null, Constants.INVALID_PARTNER_CREDENTIALS, DateOperations.getDateString(date), 0);
            } else {
                if(isPartner){
                    buildings = buildingService.findByParentPartner(retrievedPartner.getId());
                } else {
                    Building retrievedBuilding = buildingService.findByPartner(retrievedPartner.getId());

                    if (retrievedBuilding != null) {
                        buildingName = retrievedBuilding.getBuildingName();
                        buildings.add(buildingService.findByPartner(retrievedPartner.getId()));
                    }
                }
            }
        }

        TransactionLogMaster transactionLogMaster = new TransactionLogMaster();
        transactionLogMaster.setStatus(1);
        transactionLogMaster.setBuildingName(buildingName);
        transactionLogMaster.setAmountMap(initAmountsMap());
        transactionLogMaster.setDate(DateOperations.getDateString(date));

        fetchUserList(date, buildings, transactionLogMaster);

        return transactionLogMaster;
    }

    public TransactionLogMaster fetchTransactionLogs(BuildingDateBean buildingDateBean) {
        Date date = DateOperations.getClientToServerCustomStartDateFromLong(buildingDateBean.getDate(), 0);

        Building retrievedBuilding = buildingService.findById(buildingDateBean.getBuildingId());

        if (retrievedBuilding == null) {
            debugLogger.debug(TAG + "No Building found for Id : " + buildingDateBean.getBuildingId());
            return new TransactionLogMaster(null, null, null, null, null, null, null, Constants.SOMETHING_WENT_WRONG, DateOperations.getDateString(date), 0);
        }

        List<Building> buildings = new ArrayList<>();
        buildings.add(buildingService.findById(buildingDateBean.getBuildingId()));

        TransactionLogMaster transactionLogMaster = new TransactionLogMaster();
        transactionLogMaster.setStatus(1);
        transactionLogMaster.setBuildingName(buildings.get(0).getBuildingName());
        transactionLogMaster.setAmountMap(initAmountsMap());
        transactionLogMaster.setDate(DateOperations.getDateString(date));

        return fetchUserList(date, buildings, transactionLogMaster);
    }

    private HashMap<String, Double> initAmountsMap(){
        HashMap<String, Double> amountsMap = new LinkedHashMap<>();
        amountsMap.put(Constants.CASH_TOTAL, 0.0);
        amountsMap.put(Constants.ONLINE_TOTAL, 0.0);
        amountsMap.put(Constants.COUPON_REFUND_TOTAL, 0.0);
        amountsMap.put(Constants.NON_DELIVERY_REFUND_TOTAL, 0.0);
        amountsMap.put(Constants.OTHERS_TOTAL, 0.0);
        amountsMap.put(Constants.TOTAL_AMOUNT, 0.0);
        return amountsMap;
    }

    private TransactionLogMaster fetchUserList(Date date, List<Building> buildings, TransactionLogMaster transactionLogMaster){
        List<User> users = userService.findByBuildings(buildings);

        return fetchBillingMasterList(date, users, transactionLogMaster);
    }

    private TransactionLogMaster fetchBillingMasterList(Date date, List<User> users, TransactionLogMaster transactionLogMaster){
        List<BillingMaster> billingMasters = billingMasterService.findByDateAndUserList(users, date);

        return populateTransactionLogsList(billingMasters, transactionLogMaster);
    }

    private TransactionLogMaster populateTransactionLogsList(List<BillingMaster> billingMasters, TransactionLogMaster transactionLogMaster){
        for(BillingMaster billingMaster: billingMasters){
            TransactionLog transactionLog = buildTransactionLog(billingMaster);

            PaymentType paymentType = billingMaster.getPaymentType();

            switch (paymentType.getType()){
                case 1 :
                    transactionLogMaster.getCashTransactions().add(transactionLog);
                    transactionLogMaster.getAmountMap().put(Constants.CASH_TOTAL, transactionLogMaster.getAmountMap().get(Constants.CASH_TOTAL) + transactionLog.getAmount());
                    transactionLogMaster.getAmountMap().put(Constants.TOTAL_AMOUNT, transactionLogMaster.getAmountMap().get(Constants.TOTAL_AMOUNT) + transactionLog.getAmount());
                    break;
                case 2 :
                    transactionLogMaster.getCouponRefundTransactions().add(transactionLog);
                    transactionLogMaster.getAmountMap().put(Constants.COUPON_REFUND_TOTAL, transactionLogMaster.getAmountMap().get(Constants.COUPON_REFUND_TOTAL) + transactionLog.getAmount());
                    transactionLogMaster.getAmountMap().put(Constants.TOTAL_AMOUNT, transactionLogMaster.getAmountMap().get(Constants.TOTAL_AMOUNT) - transactionLog.getAmount());
                    break;
                case 3 :
                    transactionLogMaster.getOnlineTransactions().add(transactionLog);
                    transactionLogMaster.getAmountMap().put(Constants.ONLINE_TOTAL, transactionLogMaster.getAmountMap().get(Constants.ONLINE_TOTAL) + transactionLog.getAmount());
                    transactionLogMaster.getAmountMap().put(Constants.TOTAL_AMOUNT, transactionLogMaster.getAmountMap().get(Constants.TOTAL_AMOUNT) + transactionLog.getAmount());
                    break;
                case 4 :
                    transactionLogMaster.getNonDeliveryRefundTransactions().add(transactionLog);
                    transactionLogMaster.getAmountMap().put(Constants.NON_DELIVERY_REFUND_TOTAL, transactionLogMaster.getAmountMap().get(Constants.NON_DELIVERY_REFUND_TOTAL) + transactionLog.getAmount());
                    transactionLogMaster.getAmountMap().put(Constants.TOTAL_AMOUNT, transactionLogMaster.getAmountMap().get(Constants.TOTAL_AMOUNT) - transactionLog.getAmount());
                    break;
                case 5 :
                    transactionLogMaster.getOtherTransactions().add(transactionLog);
                    transactionLogMaster.getAmountMap().put(Constants.OTHERS_TOTAL, transactionLogMaster.getAmountMap().get(Constants.OTHERS_TOTAL) + transactionLog.getAmount());
                    transactionLogMaster.getAmountMap().put(Constants.TOTAL_AMOUNT, transactionLogMaster.getAmountMap().get(Constants.TOTAL_AMOUNT) - transactionLog.getAmount());
                    break;
                default:
                    break;
            }
        }

        return populateTransactionLogAmountBeans(transactionLogMaster);
    }

    private TransactionLog buildTransactionLog(BillingMaster billingMaster){
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setUserName(billingMaster.getUser().getUserName());
        transactionLog.setBuildingName(billingMaster.getUser().getBuilding().getBuildingName());
        transactionLog.setFlat(billingMaster.getUser().getFlat());
        transactionLog.setBlockName(billingMaster.getUser().getBlock().getBlockName());
        transactionLog.setAmount(billingMaster.getAmount());
        transactionLog.setCashback(billingMaster.getCashback());
        transactionLog.setBankTxnId(billingMaster.getBankTxnId());
        transactionLog.setDescription(billingMaster.getDescription());
        return transactionLog;
    }

    private TransactionLogMaster populateTransactionLogAmountBeans(TransactionLogMaster transactionLogMaster){
        for (String key : transactionLogMaster.getAmountMap().keySet()) {
            TransactionLogAmountBean transactionLogAmountBean = new TransactionLogAmountBean();
            transactionLogAmountBean.setLabel(key);
            transactionLogAmountBean.setValue(transactionLogMaster.getAmountMap().get(key));
            transactionLogMaster.getTransactionLogAmountBeans().add(transactionLogAmountBean);
        }

        return transactionLogMaster;
    }

}