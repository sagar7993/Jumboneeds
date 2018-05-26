package com.jumboneeds.services;

import com.jumboneeds.beans.*;
import com.jumboneeds.utils.Constants;
import com.jumboneeds.utils.DateOperations;
import org.apache.commons.collections4.comparators.ComparatorChain;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class InventoryService {
    private static final String TAG = "InventoryService : ";

    @Autowired
    private FilterSubscriptionBeanService filterSubscriptionBeanService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public InventoryMaster fetchInventory(NoAuthLogin noAuthLogin){
        Date date;

        if (noAuthLogin.getDateString() != null) {
            date = DateOperations.getCustomStartDateFromString(noAuthLogin.getDateString());
        } else {
            date = DateOperations.getClientToServerCustomStartDateFromLong(noAuthLogin.getDate(), 0);
        }

        List<FilteredSubscriptionBean> filteredSubscriptionBeans = filterSubscriptionBeanService.fetchFilteredSubscriptions(null, date, false);

        if (CollectionUtils.isEmpty(filteredSubscriptionBeans)){
            debugLogger.debug(TAG + Constants.NO_SUBSCRIPTIONS_FOUND_FOR_DATE + " : " + date);

            return new InventoryMaster(null, null, DateOperations.getDateString(date), 0.0, 0, 0, 1);
        }

        //Initialize AnalyticsMaster
        InventoryMaster inventoryMaster = new InventoryMaster();
        inventoryMaster.setStatus(1);
        inventoryMaster.setDate(DateOperations.getDateString(date));
        inventoryMaster.setOrderCount(filteredSubscriptionBeans.size());

        return populateInventoryProductMap(filteredSubscriptionBeans, inventoryMaster);
    }

    private InventoryMaster populateInventoryProductMap(List<FilteredSubscriptionBean> filteredSubscriptionBeans, InventoryMaster inventoryMaster){
        Set<String> userSet = new HashSet<>();

        Map<String, InventoryProduct> inventoryProductMap = new HashMap<>();

        for(FilteredSubscriptionBean filteredSubscriptionBean : filteredSubscriptionBeans){
            InventoryProduct inventoryProduct = new InventoryProduct();

            String key = filteredSubscriptionBean.getProductName().trim() + " " + filteredSubscriptionBean.getProductUnitSize().trim();

            Double productUnitPrice;

            if (filteredSubscriptionBean.getSubscriptionType() == 4){
                productUnitPrice = filteredSubscriptionBean.getSubscriptionPrice();
            } else {
                productUnitPrice = filteredSubscriptionBean.getProductUnitPrice();
            }

            userSet.add(filteredSubscriptionBean.getUserId());

            if(inventoryProductMap.containsKey(key)){
                inventoryProduct = inventoryProductMap.get(key);
                inventoryProduct.setProductQuantity(inventoryProduct.getProductQuantity() + filteredSubscriptionBean.getProductQuantity());
                inventoryProduct.setTotalAmount(inventoryProduct.getProductQuantity() * productUnitPrice);
                inventoryProductMap.put(key, inventoryProduct);
            } else {
                inventoryProduct.setProductCategoryType(filteredSubscriptionBean.getProductCategoryType());
                inventoryProduct.setProductCategoryName(filteredSubscriptionBean.getProductCategoryName());
                inventoryProduct.setProductSubCategoryType(filteredSubscriptionBean.getProductSubCategoryType());
                inventoryProduct.setProductSubCategoryName(filteredSubscriptionBean.getProductSubCategoryName());
                inventoryProduct.setProductName(filteredSubscriptionBean.getProductName().trim());
                inventoryProduct.setProductUnitSize(filteredSubscriptionBean.getProductUnitSize().trim());
                inventoryProduct.setProductUnitPrice(productUnitPrice);
                inventoryProduct.setProductQuantity(filteredSubscriptionBean.getProductQuantity());
                inventoryProduct.setTotalAmount(filteredSubscriptionBean.getProductQuantity() * productUnitPrice);
                inventoryProduct.setFulfilledByPartner(filteredSubscriptionBean.getFulfilledByPartner());
                inventoryProductMap.put(key, inventoryProduct);
            }
        }

        //Update AnalyticsMaster
        inventoryMaster.setUserCount(userSet.size());

        return populateInventoryProducts(filteredSubscriptionBeans, inventoryProductMap, inventoryMaster);
    }

    private InventoryMaster populateInventoryProducts(List<FilteredSubscriptionBean> filteredSubscriptionBeans, Map<String, InventoryProduct> inventoryProductMap, InventoryMaster inventoryMaster){
        List<InventoryProduct> inventoryProducts = new ArrayList<>();

        double totalAmountInventory = 0;

        for(String key : inventoryProductMap.keySet()){
            InventoryProduct inventoryProduct = inventoryProductMap.get(key);
            inventoryProducts.add(inventoryProduct);
            totalAmountInventory = totalAmountInventory + inventoryProduct.getTotalAmount();
        }

        //Update AnalyticsMaster
        inventoryMaster.setInventoryProducts(inventoryProducts);
        inventoryMaster.setTotalAmount(totalAmountInventory);

        return populateInventoryBuildingMap(filteredSubscriptionBeans, inventoryMaster);
    }

    @SuppressWarnings("unchecked")
    private InventoryMaster populateInventoryBuildingMap(List<FilteredSubscriptionBean> filteredSubscriptionBeans, InventoryMaster inventoryMaster){
        List<InventoryBuilding> inventoryBuildings = new ArrayList<>();

        for(FilteredSubscriptionBean filteredSubscriptionBean : filteredSubscriptionBeans){
            Double productUnitPrice;

            if (filteredSubscriptionBean.getSubscriptionType() == 4 || filteredSubscriptionBean.getFlashSaleProduct()){
                productUnitPrice = filteredSubscriptionBean.getSubscriptionPrice();
            } else {
                productUnitPrice = filteredSubscriptionBean.getProductUnitPrice();
            }

            InventoryBuilding inventoryBuilding = new InventoryBuilding();
            inventoryBuilding.setBuildingName(filteredSubscriptionBean.getBuildingName());
            inventoryBuilding.setUserName(filteredSubscriptionBean.getUserName());
            inventoryBuilding.setBlock(filteredSubscriptionBean.getBlock());
            inventoryBuilding.setFlat(filteredSubscriptionBean.getFlat());
            inventoryBuilding.setProductSubCategoryType(filteredSubscriptionBean.getProductSubCategoryType());
            inventoryBuilding.setProductSubCategoryName(filteredSubscriptionBean.getProductSubCategoryName());
            inventoryBuilding.setProductCategoryType(filteredSubscriptionBean.getProductCategoryType());
            inventoryBuilding.setProductCategoryName(filteredSubscriptionBean.getProductCategoryName());
            inventoryBuilding.setProductUnitPrice(productUnitPrice);
            inventoryBuilding.setProductName(filteredSubscriptionBean.getProductName().trim());
            inventoryBuilding.setProductUnitSize(filteredSubscriptionBean.getProductUnitSize().trim());
            inventoryBuilding.setProductQuantity(filteredSubscriptionBean.getProductQuantity());
            inventoryBuilding.setTotalAmount(inventoryBuilding.getProductQuantity() * productUnitPrice);
            inventoryBuildings.add(inventoryBuilding);
        }

        ComparatorChain comparatorChain = new ComparatorChain();
        comparatorChain.addComparator(new InventoryBuilding().getBuildingComparator());
        comparatorChain.addComparator(new InventoryBuilding().getMergedBlockAndFlatComparator());

        //Sort InventoryBuildings
        Collections.sort(inventoryBuildings, comparatorChain);

        //Update InventoryMaster
        inventoryMaster.setInventoryBuildings(inventoryBuildings);

        return inventoryMaster;
    }

}