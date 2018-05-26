package com.jumboneeds.services;

import com.jumboneeds.beans.FilteredSubscriptionBean;
import com.jumboneeds.beans.SubscriptionExceptionBean;
import com.jumboneeds.entities.Building;
import com.jumboneeds.entities.Product;
import com.jumboneeds.utils.DateOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by akash.mercer on 15-Oct-16.
 */

@Service
public class FilterSubscriptionBeanService {
    private static final String TAG = "FilterSubscriptionBeanService : ";

    @Autowired
    private ProductService productService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private SubscriptionExceptionService subscriptionExceptionService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public List<FilteredSubscriptionBean> fetchFilteredSubscriptionsForUser(String userId, Date date){
        List<FilteredSubscriptionBean> filteredSubscriptionBeans = subscriptionService.findSubscriptionBeansByUser(userId, date);

        Map<String, FilteredSubscriptionBean> filteredSubscriptionBeanMap = new HashMap<>();

        if (CollectionUtils.isEmpty(filteredSubscriptionBeans)){
            return filteredSubscriptionBeans;
        }

        for(FilteredSubscriptionBean filteredSubscriptionBean : filteredSubscriptionBeans){
            filteredSubscriptionBeanMap.put(filteredSubscriptionBean.getId(), filteredSubscriptionBean);
        }

        return fetchSubscriptionExceptions(filteredSubscriptionBeans, filteredSubscriptionBeanMap, date);
    }

    public List<FilteredSubscriptionBean> fetchFilteredSubscriptions(List<Building> buildings, Date date, boolean includePartnerProducts){
        List<Product> products = new ArrayList<>();

        if(!CollectionUtils.isEmpty(buildings)){
            products = productService.findByBuildings(buildings);
        }

        return fetchSubscriptions(products, date, includePartnerProducts);
    }

    private List<FilteredSubscriptionBean> fetchSubscriptions(List<Product> products, Date date, boolean includePartnerProducts){
        List<FilteredSubscriptionBean> filteredSubscriptionBeans;

        Map<String, FilteredSubscriptionBean> filteredSubscriptionBeanMap = new HashMap<>();

        if(!CollectionUtils.isEmpty(products)){
            if(includePartnerProducts){
                filteredSubscriptionBeans = subscriptionService.findSubscriptionBeansByProductsAndDate(products, date);
            } else {
                filteredSubscriptionBeans = subscriptionService.findSubscriptionBeansByProductsAndDateWithoutPartnerProducts(products, date);
            }

            if(CollectionUtils.isEmpty(filteredSubscriptionBeans)){
                debugLogger.debug(TAG + "No filteredSubscriptionBeans found for products on " + date);
            }
        } else {
            if(includePartnerProducts){
                filteredSubscriptionBeans = subscriptionService.fetchSubscriptionBeansByDate(date);
            } else {
                filteredSubscriptionBeans = subscriptionService.fetchSubscriptionBeansByDateWithoutPartnerProducts(date);
            }

            if(CollectionUtils.isEmpty(filteredSubscriptionBeans)){
                debugLogger.debug(TAG + "No filteredSubscriptionBeans found for users on " + date);
            }
        }

        if(CollectionUtils.isEmpty(filteredSubscriptionBeans)){
            return filteredSubscriptionBeans;
        }

        for(FilteredSubscriptionBean filteredSubscriptionBean : filteredSubscriptionBeans){
            filteredSubscriptionBeanMap.put(filteredSubscriptionBean.getId(), filteredSubscriptionBean);
        }

        return fetchSubscriptionExceptions(filteredSubscriptionBeans, filteredSubscriptionBeanMap, date);
    }

    private List<FilteredSubscriptionBean> fetchSubscriptionExceptions(List<FilteredSubscriptionBean> filteredSubscriptionBeans, Map<String, FilteredSubscriptionBean> filteredSubscriptionBeanMap, Date date){
        List<String> subscriptionIds = new ArrayList<>();
        subscriptionIds.addAll(filteredSubscriptionBeanMap.keySet());

        List<SubscriptionExceptionBean> subscriptionExceptionBeans = subscriptionExceptionService.findSubscriptionExceptionBeansBySubscriptionsAndDate(subscriptionIds, date);

        Set<String> subscriptionExceptionSet = new HashSet<>();

        for(SubscriptionExceptionBean subscriptionExceptionBean : subscriptionExceptionBeans){
            FilteredSubscriptionBean filteredSubscriptionBean = filteredSubscriptionBeanMap.get(subscriptionExceptionBean.getSubscriptionId());

            if(subscriptionExceptionSet.contains(subscriptionExceptionBean.getId())){
                continue;
            } else {
                subscriptionExceptionSet.add(subscriptionExceptionBean.getId());
                filteredSubscriptionBean.setProductQuantity(subscriptionExceptionBean.getProductQuantity());
            }
        }

        for (Iterator<FilteredSubscriptionBean> iterator = filteredSubscriptionBeans.iterator(); iterator.hasNext(); ) {
            FilteredSubscriptionBean filteredSubscriptionBean = iterator.next();

            if(filteredSubscriptionBean.getProductQuantity() == 0){
                iterator.remove();
            }
        }

        return filterSubscriptions(filteredSubscriptionBeans, date);
    }

    private List<FilteredSubscriptionBean> filterSubscriptions(List<FilteredSubscriptionBean> finalSubscriptionBeans, Date date){
        for (Iterator<FilteredSubscriptionBean> iterator = finalSubscriptionBeans.iterator(); iterator.hasNext(); ) {
            FilteredSubscriptionBean filteredSubscriptionBean = iterator.next();

            int subscriptionType = filteredSubscriptionBean.getSubscriptionType();
            Date startDate = filteredSubscriptionBean.getStartDate();

            if(DateOperations.getDateDifference(date, startDate) % subscriptionType != 0){
                iterator.remove();
            }
        }

        return finalSubscriptionBeans;
    }
}
