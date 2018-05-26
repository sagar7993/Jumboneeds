package com.jumboneeds.services;

import com.jumboneeds.beans.*;
import com.jumboneeds.entities.Building;
import com.jumboneeds.entities.Partner;
import com.jumboneeds.utils.DateOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.jumboneeds.utils.Constants.NO_BUILDINGS_FOUND;
import static com.jumboneeds.utils.Constants.NO_SUBSCRIPTIONS_FOUND_FOR_BUILDING;

@Service
public class AnalyticsService {
    private static final String TAG = "AnalyticsService : ";

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private FilterSubscriptionBeanService filterSubscriptionBeanService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public AnalyticsMaster fetchAnalytics(PartnerLogin partnerLogin, boolean isPartner){
        Date date = DateOperations.getCustomStartDateFromString(partnerLogin.getDateString());

        List<Building> buildings = new ArrayList<>();

        String buildingName = "JumboNeeds";

        if(!StringUtils.isEmpty(partnerLogin.getUserName()) && !StringUtils.isEmpty(partnerLogin.getPassword())){
            Partner partner = partnerService.partnerWebLogin(partnerLogin.getUserName().trim(), partnerLogin.getPassword().trim());

            if(partner == null){
                return new AnalyticsMaster(null, null, null, null, 0.0, 0, 0, 0);
            }

            buildingName = partner.getBuildingName();

            if(isPartner){
                buildings = buildingService.findByParentPartner(partner.getId());
            } else {
                buildings.add(buildingService.findByPartner(partner.getId()));
            }

            if(CollectionUtils.isEmpty(buildings)){
                debugLogger.debug(TAG + NO_BUILDINGS_FOUND + " : " + partner.getId());

                return new AnalyticsMaster(null, null, buildingName, DateOperations.getDateStringForAdmin(date), 0.0, 0, 0, 0);
            }
        }

        List<FilteredSubscriptionBean> filteredSubscriptionBeans = filterSubscriptionBeanService.fetchFilteredSubscriptions(buildings, date, true);

        if (CollectionUtils.isEmpty(filteredSubscriptionBeans)){
            debugLogger.debug(TAG + NO_SUBSCRIPTIONS_FOUND_FOR_BUILDING + " : " + buildingName);

            return new AnalyticsMaster(null, null, buildingName, DateOperations.getDateStringForAdmin(date), 0.0, 0, 0, 1);
        }

        //Initialize AnalyticsMaster
        AnalyticsMaster analyticsMaster = new AnalyticsMaster();
        analyticsMaster.setStatus(1);
        analyticsMaster.setBuildingName(buildingName);
        analyticsMaster.setDate(DateOperations.getDateString(date));
        analyticsMaster.setOrderCount(filteredSubscriptionBeans.size());

        return populateAnalyticsProductMap(filteredSubscriptionBeans, analyticsMaster);
    }

    private AnalyticsMaster populateAnalyticsProductMap(List<FilteredSubscriptionBean> filteredSubscriptionBeans, AnalyticsMaster analyticsMaster){
        Set<String> userSet = new HashSet<>();

        Map<String, AnalyticsProduct> analyticsProductMap = new HashMap<>();

        for(FilteredSubscriptionBean filteredSubscriptionBean : filteredSubscriptionBeans){
            AnalyticsProduct analyticsProduct = new AnalyticsProduct();

            String key = filteredSubscriptionBean.getProductName().trim() + " " + filteredSubscriptionBean.getProductUnitSize().trim();

            Double productUnitPrice;

            if (filteredSubscriptionBean.getSubscriptionType() == 4 || filteredSubscriptionBean.getFlashSaleProduct()){
                productUnitPrice = filteredSubscriptionBean.getSubscriptionPrice();
            } else {
                productUnitPrice = filteredSubscriptionBean.getProductUnitPrice();
            }

            userSet.add(filteredSubscriptionBean.getUserId());

            if(analyticsProductMap.containsKey(key)){
                analyticsProduct = analyticsProductMap.get(key);
                analyticsProduct.setProductQuantity(analyticsProduct.getProductQuantity() + filteredSubscriptionBean.getProductQuantity());
                analyticsProduct.setTotalAmount(analyticsProduct.getProductQuantity() * productUnitPrice);
                analyticsProductMap.put(key, analyticsProduct);
            } else {
                analyticsProduct.setProductSubCategoryType(filteredSubscriptionBean.getProductSubCategoryType());
                analyticsProduct.setProductSubCategoryName(filteredSubscriptionBean.getProductSubCategoryName());
                analyticsProduct.setProductCategoryType(filteredSubscriptionBean.getProductCategoryType());
                analyticsProduct.setProductCategoryName(filteredSubscriptionBean.getProductCategoryName());
                analyticsProduct.setProductName(filteredSubscriptionBean.getProductName());
                analyticsProduct.setProductUnitSize(filteredSubscriptionBean.getProductUnitSize());
                analyticsProduct.setProductUnitPrice(productUnitPrice);
                analyticsProduct.setProductQuantity(filteredSubscriptionBean.getProductQuantity());
                analyticsProduct.setTotalAmount(filteredSubscriptionBean.getProductQuantity() * productUnitPrice);
                analyticsProductMap.put(key, analyticsProduct);
            }

            analyticsProduct.setTotalAmount(analyticsProduct.getProductQuantity() * productUnitPrice);
        }

        //Update AnalyticsMaster
        analyticsMaster.setUserCount(userSet.size());

        return populateAnalyticsProducts(analyticsProductMap, analyticsMaster);
    }

    private AnalyticsMaster populateAnalyticsProducts(Map<String, AnalyticsProduct> analyticsProductMap, AnalyticsMaster analyticsMaster){
        List<AnalyticsProduct> analyticsProducts = new ArrayList<>();

        for(String key : analyticsProductMap.keySet()){
            analyticsProducts.add(analyticsProductMap.get(key));
        }

        //Update AnalyticsMaster
        analyticsMaster.setAnalyticsProducts(analyticsProducts);

        return populateAnalyticsCategoryMap(analyticsProducts, analyticsMaster);
    }

    private AnalyticsMaster populateAnalyticsCategoryMap(List<AnalyticsProduct> analyticsProducts, AnalyticsMaster analyticsMaster){
        Map<Integer, AnalyticsCategory> analyticsCategoryMap = new HashMap<>();

        for(AnalyticsProduct analyticsProduct : analyticsProducts){
            AnalyticsCategory analyticsCategory = new AnalyticsCategory();
            int key = analyticsProduct.getProductSubCategoryType();

            if(analyticsCategoryMap.containsKey(key)){
                analyticsCategory = analyticsCategoryMap.get(key);
                analyticsCategory.setProductQuantity(analyticsCategory.getProductQuantity() + analyticsProduct.getProductQuantity());
                analyticsCategory.setTotalAmount(analyticsCategory.getTotalAmount() + analyticsProduct.getTotalAmount());
            } else {
                analyticsCategory.setProductSubCategoryType(key);
                analyticsCategory.setProductSubCategoryName(analyticsProduct.getProductSubCategoryName());
                analyticsCategory.setProductCategoryType(analyticsProduct.getProductCategoryType());
                analyticsCategory.setProductCategoryName(analyticsProduct.getProductCategoryName());
                analyticsCategory.setProductQuantity(analyticsProduct.getProductQuantity());
                analyticsCategory.setTotalAmount(analyticsProduct.getTotalAmount());
                analyticsCategoryMap.put(key, analyticsCategory);
            }
        }

        return populateAnalyticsCategories(analyticsCategoryMap, analyticsMaster);
    }

    private AnalyticsMaster populateAnalyticsCategories(Map<Integer, AnalyticsCategory> analyticsCategoryMap, AnalyticsMaster analyticsMaster){
        List<AnalyticsCategory> analyticsCategories = new ArrayList<>();

        double totalAmountAnalytics = 0;

        for(Integer key : analyticsCategoryMap.keySet()){
            AnalyticsCategory analyticsCategory = analyticsCategoryMap.get(key);
            analyticsCategories.add(analyticsCategory);
            totalAmountAnalytics = totalAmountAnalytics + analyticsCategory.getTotalAmount();
        }

        //Update AnalyticsMaster
        analyticsMaster.setAnalyticsCategories(analyticsCategories);
        analyticsMaster.setTotalAmount(totalAmountAnalytics);

        return analyticsMaster;
    }

}