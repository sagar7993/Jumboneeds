package com.jumboneeds.services;

import com.jumboneeds.beans.*;
import com.jumboneeds.entities.BillingMaster;
import com.jumboneeds.entities.User;
import com.jumboneeds.repositories.BillingMasterRepository;
import com.jumboneeds.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BillingMasterService {
    private static final String TAG = "BillingMasterService : ";

    @Autowired
    private BillingMasterRepository billingMasterRepository;

    @Autowired
    private UserService userService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public BillingMaster save(BillingMaster billingMaster) {
        return billingMasterRepository.save(billingMaster);
    }

    public BillingMaster findById(String id) {
        return billingMasterRepository.findById(id);
    }

    public Double getAmountForBillingMaster(String id) {
        return billingMasterRepository.getAmountForBillingMaster(id);
    }

    public WalletBalanceBean findByUser(IdBean idBean) {
        WalletBalanceBean walletBalanceBean = new WalletBalanceBean();

        User retrievedUser = userService.findById(idBean.getId());

        if(retrievedUser == null) {
            debugLogger.debug(TAG + "No User found for Id : " + idBean.getId());
            walletBalanceBean.setStatus(0);
            walletBalanceBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return walletBalanceBean;
        }

        walletBalanceBean.setBillingMasterBean(billingMasterRepository.findByUser(idBean.getId()));
        walletBalanceBean.setStatus(1);

        return walletBalanceBean;
    }

    public List<BillingMaster> fetchCurrentBillingMasters() {
        return billingMasterRepository.fetchCurrentBillingMasters();
    }

    public List<BillingMasterBean> fetchCurrentBillingMasterBeans() {
        return billingMasterRepository.fetchCurrentBillingMasterBeans();
    }

    public List<BillingMaster> findByDateAndUserList(List<User> users, Date date) {
        if(!CollectionUtils.isEmpty(users)) {
            return billingMasterRepository.findByDateAndUserList(users, date);
        } else {
            return new ArrayList<>();
        }
    }

    public List<LowBalanceUserBean> findByLowAmount(Double amount) {
        return billingMasterRepository.findByLowAmount(amount);
    }

    public TransactionHistoryBean getTransactionHistory(IdBean idBean) {
        TransactionHistoryBean transactionHistoryBean = new TransactionHistoryBean();

        User retrievedUser = userService.findById(idBean.getId());

        if(retrievedUser == null) {
            debugLogger.debug(TAG + "No User found for Id : " + idBean.getId());
            transactionHistoryBean.setStatus(0);
            transactionHistoryBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return transactionHistoryBean;
        }

        transactionHistoryBean.setTransactions(billingMasterRepository.getTransactionHistory(idBean.getId()));
        transactionHistoryBean.setStatus(1);

        return transactionHistoryBean;
    }

}