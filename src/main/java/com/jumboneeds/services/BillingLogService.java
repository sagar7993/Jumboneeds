package com.jumboneeds.services;

import com.jumboneeds.beans.BillingLogBean;
import com.jumboneeds.beans.BillingLogHistoryBean;
import com.jumboneeds.beans.IdBean;
import com.jumboneeds.entities.BillingLog;
import com.jumboneeds.entities.User;
import com.jumboneeds.repositories.BillingLogRepository;
import com.jumboneeds.utils.Constants;
import com.jumboneeds.utils.DateOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BillingLogService {
    private static final String TAG = "BillingLogService : ";

    @Autowired
    private BillingLogRepository billingLogRepository;

    @Autowired
    private UserService userService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    // TODO: 24-Aug-16 Pagination is required for below service
    public BillingLogHistoryBean findByUser(IdBean idBean){
        BillingLogHistoryBean billingLogHistoryBean = new BillingLogHistoryBean();

        User retrievedUser = userService.findById(idBean.getId());

        if(retrievedUser == null){
            debugLogger.debug(TAG + "No User found for Id : " + idBean.getId());
            billingLogHistoryBean.setStatus(0);
            billingLogHistoryBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return billingLogHistoryBean;
        }

        billingLogHistoryBean.setBillingLogBeans(billingLogRepository.findByUser(retrievedUser.getId()));
        billingLogHistoryBean.setStatus(1);

        return billingLogHistoryBean;
    }

    public void save(BillingLog billingLog){
        billingLogRepository.save(billingLog);
    }

    public List<BillingLog> getUserBillingLogs(IdBean idBean) {
        return billingLogRepository.getUserBillingLogs(idBean.getId(), DateOperations.getClientToServerCustomStartDateFromLong(new Date().getTime(), -7), DateOperations.getClientToServerCustomStartDateFromLong(new Date().getTime(), 7));
    }

    public List<BillingLogBean> getUserBillingLogsForSchedule(IdBean idBean, int pastLimit, int futureLimit) {
        Date date = DateOperations.getTodayStartDate();
        Date minDate = DateOperations.getCustomStartDateFromDate(date, -pastLimit);
        Date maxDate = DateOperations.getCustomStartDateFromDate(date, futureLimit);

        return billingLogRepository.getUserBillingLogsForSchedule(idBean.getId(), minDate, maxDate);
    }

    public List<BillingLog> getAllUserBillingLogsBetweenDates(IdBean idBean) {
        return billingLogRepository.getAllUserBillingLogsBetweenDates(idBean.getId(), DateOperations.getClientToServerCustomStartDateFromLong(new Date().getTime(), -7), DateOperations.getClientToServerCustomStartDateFromLong(new Date().getTime(), 7));
    }

}