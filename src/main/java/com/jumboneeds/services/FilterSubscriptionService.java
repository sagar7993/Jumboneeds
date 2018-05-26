package com.jumboneeds.services;

import com.jumboneeds.entities.*;
import com.jumboneeds.utils.DateOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class FilterSubscriptionService {
    private static final String TAG = "FilterSubscriptionService : ";

    @Autowired
    private ProductService productService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private SubscriptionExceptionService subscriptionExceptionService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public List<Subscription> fetchFilteredSubscriptionsForUserList(List<User> users, Date date){
        List<String> userIds = new ArrayList<>();

        for(User user : users) {
            userIds.add(user.getId());
        }

        List<Subscription> subscriptions = subscriptionService.findSubscriptionsByUserListAndDate(userIds, date);

        Map<String, Subscription> subscriptionMap = new HashMap<>();

        if (CollectionUtils.isEmpty(subscriptions)){
            debugLogger.debug(TAG + "No subscriptions found for users on " + date);
            return subscriptions;
        }

        for(Subscription subscription : subscriptions){
            subscription.setTransientProductQuantity(subscription.getProductQuantity());
            subscriptionMap.put(subscription.getId(), subscription);
        }

        return fetchSubscriptionExceptions(subscriptions, subscriptionMap, date);
    }

    public List<Subscription> fetchFilteredSubscriptions(List<Building> buildings, Date date, boolean includePartnerProducts){
        List<Product> products = new ArrayList<>();

        if(!CollectionUtils.isEmpty(buildings)){
            products = productService.findByBuildings(buildings);
        }

        return fetchSubscriptions(products, date, includePartnerProducts);
    }

    private List<Subscription> fetchSubscriptions(List<Product> products, Date date, boolean includePartnerProducts){
        List<Subscription> subscriptions;

        Map<String, Subscription> subscriptionMap = new HashMap<>();

        if(!CollectionUtils.isEmpty(products)){
            if(includePartnerProducts){
                subscriptions = subscriptionService.findByProductsAndDate(products, date);
            } else {
                subscriptions = subscriptionService.findByProductsAndDateWithoutPartnerProducts(products, date);
            }

            if(CollectionUtils.isEmpty(subscriptions)){
                debugLogger.debug(TAG + "No subscriptions found for products on " + date);
            }
        } else {
            if(includePartnerProducts){
                subscriptions = subscriptionService.fetchActiveSubscriptionsByDate(date);
            } else {
                subscriptions = subscriptionService.fetchActiveSubscriptionsByDateWithoutPartnerProducts(date);
            }

            if(CollectionUtils.isEmpty(subscriptions)){
                debugLogger.debug(TAG + "No subscriptions found for users on " + date);
            }
        }

        if(CollectionUtils.isEmpty(subscriptions)){
            return subscriptions;
        }

        for(Subscription subscription : subscriptions){
            subscription.setTransientProductQuantity(subscription.getProductQuantity());
            subscriptionMap.put(subscription.getId(), subscription);
        }

        return fetchSubscriptionExceptions(subscriptions, subscriptionMap, date);
    }

    private List<Subscription> fetchSubscriptionExceptions(List<Subscription> subscriptions, Map<String, Subscription> subscriptionMap, Date date){
        List<SubscriptionException> subscriptionExceptions = subscriptionExceptionService.findBySubscriptionsAndDate(subscriptions, date);

        Set<String> subscriptionExceptionSet = new HashSet<>();

        for(SubscriptionException subscriptionException : subscriptionExceptions){
            Subscription subscription = subscriptionException.getSubscription();

            if(subscriptionExceptionSet.contains(subscriptionException.getId())){
                continue;
            } else {
                subscriptionExceptionSet.add(subscriptionException.getId());
                subscription.setTransientProductQuantity(subscriptionException.getProductQuantity());
            }

            if(subscription.getTransientProductQuantity() == 0){
                subscriptionMap.remove(subscription.getId());
            }
        }

        List<Subscription> finalSubscriptions = new ArrayList<>();
        finalSubscriptions.addAll(subscriptionMap.values());

        return filterSubscriptions(finalSubscriptions, date);
    }

    private List<Subscription> filterSubscriptions(List<Subscription> subscriptions, Date date){
        for (Iterator<Subscription> iterator = subscriptions.iterator(); iterator.hasNext(); ) {
            Subscription subscription = iterator.next();

            int subscriptionType = subscription.getSubscriptionType().getType();
            Date startDate = subscription.getStartDate();

            if(DateOperations.getDateDifference(date, startDate) % subscriptionType != 0){
                iterator.remove();
            }
        }

        return subscriptions;
    }
}