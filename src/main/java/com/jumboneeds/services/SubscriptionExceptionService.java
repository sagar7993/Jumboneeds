package com.jumboneeds.services;

import com.jumboneeds.beans.AdminExceptionBean;
import com.jumboneeds.beans.StatusBean;
import com.jumboneeds.beans.SubscriptionExceptionBean;
import com.jumboneeds.beans.SubscriptionExceptionUpdateBean;
import com.jumboneeds.entities.Subscription;
import com.jumboneeds.entities.SubscriptionException;
import com.jumboneeds.repositories.SubscriptionExceptionRepository;
import com.jumboneeds.utils.Constants;
import com.jumboneeds.utils.DateOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SubscriptionExceptionService {
    private static final String TAG = "SubscriptionExceptionService : ";

    @Autowired
    private SubscriptionExceptionRepository subscriptionExceptionRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private SubscriptionExceptionTypeService subscriptionExceptionTypeService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public void save(SubscriptionException subscriptionException) {
        subscriptionExceptionRepository.save(subscriptionException);
    }

    public SubscriptionExceptionUpdateBean saveSubscriptionException(SubscriptionExceptionBean subscriptionExceptionBean){
        SubscriptionExceptionUpdateBean subscriptionExceptionUpdateBean = new SubscriptionExceptionUpdateBean();

        Subscription retrievedSubscription = subscriptionService.findById(subscriptionExceptionBean.getSubscriptionId());

        if(retrievedSubscription == null){
            debugLogger.debug(TAG + "No Subscription found for Id :" + subscriptionExceptionBean.getSubscriptionId());
            subscriptionExceptionUpdateBean.setStatus(0);
            subscriptionExceptionUpdateBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return subscriptionExceptionUpdateBean;
        }

        SubscriptionException subscriptionException = new SubscriptionException();
        subscriptionException.setSubscription(retrievedSubscription);
        subscriptionException.setUser(retrievedSubscription.getUser());
        subscriptionException.setProductQuantity(subscriptionExceptionBean.getProductQuantity());
        subscriptionException.setDate(DateOperations.getClientToServerCustomStartDateFromLong(subscriptionExceptionBean.getDate(), 0));
        subscriptionException.setSubscriptionExceptionType(subscriptionExceptionTypeService.findByType(2));

        try {
            SubscriptionException savedSubscriptionException = subscriptionExceptionRepository.save(subscriptionException);

            subscriptionExceptionUpdateBean.setSubscriptionExceptionId(savedSubscriptionException.getId());
            subscriptionExceptionUpdateBean.setStatus(1);
        } catch (Exception e){
            errorLogger.error(TAG + "Error in creating SubscriptionException for Subscription : " + subscriptionExceptionBean.getSubscriptionId());

            subscriptionExceptionUpdateBean.setStatus(0);
            subscriptionExceptionUpdateBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        }

        return subscriptionExceptionUpdateBean;
    }

    public StatusBean updateSubscriptionException(SubscriptionExceptionBean subscriptionExceptionBean){
        StatusBean statusBean = new StatusBean();

        SubscriptionException retrievedSubscriptionException = subscriptionExceptionRepository.findById(subscriptionExceptionBean.getId());

        if(retrievedSubscriptionException == null){
            debugLogger.debug(TAG + "No SubscriptionException found for Id :" + subscriptionExceptionBean.getSubscriptionId());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        try {
            subscriptionExceptionRepository.update(retrievedSubscriptionException.getId(), subscriptionExceptionBean.getProductQuantity());

            statusBean.setStatus(1);
        } catch (Exception e){
            errorLogger.error(TAG + "Error in updating SubscriptionException : " + subscriptionExceptionBean.getId());
            e.printStackTrace();

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        }

        return statusBean;
    }

    public List<SubscriptionException> findBySubscriptionsAndDate(List<Subscription> subscriptions, Date date){
        return subscriptionExceptionRepository.findBySubscriptionsAndDate(subscriptions, date);
    }

    public List<SubscriptionExceptionBean> findSubscriptionExceptionBeansBySubscriptionsAndDate(List<String> subscriptionIds, Date date){
        return subscriptionExceptionRepository.findSubscriptionExceptionBeansBySubscriptionsAndDate(subscriptionIds, date);
    }

    public SubscriptionException findById(String subscriptionExceptionId){
        return subscriptionExceptionRepository.findById(subscriptionExceptionId);
    }

    public List<SubscriptionExceptionBean> findScheduledSubscriptionExceptionsByUser(String userId, int pastLimit){
        Date date = DateOperations.getClientToServerCustomStartDateFromLong(new Date().getTime(), -pastLimit);
        return subscriptionExceptionRepository.findScheduledSubscriptionExceptionsByUser(userId, date);
    }

    public StatusBean createAdminException(AdminExceptionBean adminExceptionBean) {
        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0);
        statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        Subscription retrievedSubscription = subscriptionService.findById(adminExceptionBean.getSubscriptionId());
        if (retrievedSubscription == null) {
            debugLogger.debug(TAG + "No Subscription found for Id :" + adminExceptionBean.getSubscriptionId());
            statusBean.setMessage("No Subscription found for Id :" + adminExceptionBean.getSubscriptionId());
            return statusBean;
        }
        Date date = DateOperations.getCustomStartDateFromString(adminExceptionBean.getDate());
        List<String> subscriptionIds = new ArrayList<>();
        subscriptionIds.add(retrievedSubscription.getId());
        List<SubscriptionExceptionBean> subscriptionExceptionBeans = this.findSubscriptionExceptionBeansBySubscriptionsAndDate(subscriptionIds, date);
        if(CollectionUtils.isEmpty(subscriptionExceptionBeans)) {
            if (retrievedSubscription.getProductQuantity() == adminExceptionBean.getProductQuantity()) {
                statusBean.setMessage(Constants.SAME_SUBSCRIPTION_EXCEPTION_QUANTITY);
                return statusBean;
            }
            SubscriptionException subscriptionException = new SubscriptionException();
            subscriptionException.setDate(date);
            subscriptionException.setProductQuantity(adminExceptionBean.getProductQuantity());
            subscriptionException.setSubscription(retrievedSubscription);
            subscriptionException.setUser(retrievedSubscription.getUser());
            subscriptionException.setSubscriptionExceptionType(subscriptionExceptionTypeService.findByType(2));
            try {
                subscriptionExceptionRepository.save(subscriptionException);
            } catch (Exception e){
                errorLogger.error(TAG + "Error in creating SubscriptionException for Subscription : " + retrievedSubscription.getId());
                statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
                return statusBean;
            }
        } else {
            SubscriptionExceptionBean subscriptionExceptionBean = subscriptionExceptionBeans.get(0);

            if(subscriptionExceptionBean.getProductQuantity() == adminExceptionBean.getProductQuantity()) {
                statusBean.setMessage(Constants.EXCEPTION_ALREADY_EXISTS);
                return statusBean;
            }

            subscriptionExceptionRepository.update(subscriptionExceptionBean.getId(), adminExceptionBean.getProductQuantity());
        }
        statusBean.setStatus(1);
        statusBean.setMessage(Constants.EXCEPTION_ADDED);
        return statusBean;
    }

}