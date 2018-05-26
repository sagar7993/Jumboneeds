package com.jumboneeds.services;

import com.jumboneeds.beans.IdBean;
import com.jumboneeds.beans.ScheduleBean;
import com.jumboneeds.entities.User;
import com.jumboneeds.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.jumboneeds.utils.Constants.SCHEDULE_FUTURE_LIMIT;
import static com.jumboneeds.utils.Constants.SCHEDULE_PAST_LIMIT;

@Service
public class ScheduleService {
    private static final String TAG = "ScheduleService : ";

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private SubscriptionExceptionService subscriptionExceptionService;

    @Autowired
    private BillingLogService billingLogService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public ScheduleBean getSchedule(IdBean idBean){
        ScheduleBean scheduleBean = new ScheduleBean();

        User retrievedUser = userService.findById(idBean.getId());

        if(retrievedUser == null){
            debugLogger.debug(TAG + "No User found for Id : " + idBean.getId());

            scheduleBean.setStatus(0);
            scheduleBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return scheduleBean;
        } else {
            scheduleBean.setPastLimit(SCHEDULE_PAST_LIMIT);
            scheduleBean.setFutureLimit(SCHEDULE_FUTURE_LIMIT);
            scheduleBean.setSubscriptionBeans(subscriptionService.findScheduledSubscriptionByUser(idBean.getId(), scheduleBean.getPastLimit()));
            scheduleBean.setSubscriptionExceptionBeans(subscriptionExceptionService.findScheduledSubscriptionExceptionsByUser(idBean.getId(), scheduleBean.getPastLimit()));
            scheduleBean.setBillingLogBeans(billingLogService.getUserBillingLogsForSchedule(idBean, SCHEDULE_PAST_LIMIT, SCHEDULE_FUTURE_LIMIT));

            scheduleBean.setStatus(1);
        }

        return scheduleBean;
    }

}