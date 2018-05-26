package com.jumboneeds.services;

import com.jumboneeds.beans.*;
import com.jumboneeds.entities.Building;
import com.jumboneeds.entities.Partner;
import com.jumboneeds.utils.Constants;
import com.jumboneeds.utils.DateOperations;
import org.apache.commons.collections4.comparators.ComparatorChain;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;

import static com.jumboneeds.utils.Constants.*;
import static com.jumboneeds.utils.StorePrintingUtils.*;

@Service
public class StoreService {
    private static final String TAG = "StoreService : ";

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private FilterSubscriptionBeanService filterSubscriptionBeanService;

    @Autowired
    private BillingMasterService billingMasterService;

    @Autowired
    private CouponUserService couponUserService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public StoreMaster fetchStore(PartnerLogin partnerLogin, boolean isPartner, boolean print){
        Date date = DateOperations.getCustomStartDateFromString(partnerLogin.getDateString());

        List<Building> buildings = new ArrayList<>();

        String buildingName;

        if(!StringUtils.isEmpty(partnerLogin.getUserName()) && !StringUtils.isEmpty(partnerLogin.getPassword())) {
            Partner partner = partnerService.partnerWebLogin(partnerLogin.getUserName().trim(), partnerLogin.getPassword().trim());

            if(partner == null){
                return new StoreMaster(null, null, null, DateOperations.getDateStringForAdmin(date), 0.0, 0, 0, null, 0);
            }

            buildingName = partner.getBuildingName();

            if(isPartner){
                buildings = buildingService.findByParentPartner(partner.getId());
            } else {
                buildings.add(buildingService.findByPartner(partner.getId()));
            }

            if(CollectionUtils.isEmpty(buildings)){
                debugLogger.debug(TAG + NO_BUILDINGS_FOUND + " : " + partner.getId());

                return new StoreMaster(null, null, buildingName, DateOperations.getDateStringForAdmin(date), 0.0, 0, 0, null, 0);
            }
        } else {
            debugLogger.debug(TAG + MISSING_USERNAME_OR_PASSWORD);

            return new StoreMaster(null, null, null, DateOperations.getDateStringForAdmin(date), 0.0, 0, 0, null, 0);
        }

        List<FilteredSubscriptionBean> filteredSubscriptionBeans = filterSubscriptionBeanService.fetchFilteredSubscriptions(buildings, date, true);

        if (CollectionUtils.isEmpty(filteredSubscriptionBeans)){
            debugLogger.debug(TAG + NO_SUBSCRIPTIONS_FOUND_FOR_BUILDING + " : " + buildingName);

            return new StoreMaster(null, null, buildingName, DateOperations.getDateStringForAdmin(date), 0.0, 0, 0, null, 1);
        }

        StoreMaster storeMaster = new StoreMaster();
        storeMaster.setStatus(1);
        storeMaster.setBuildingName(buildingName);
        storeMaster.setDate(DateOperations.getDateString(date));
        storeMaster.setOrderCount(filteredSubscriptionBeans.size());

        populateStoreBuildings(filteredSubscriptionBeans, storeMaster);

        if(print){
            storeMaster.setBuildingId(buildings.get(0).getId());
            printStore(storeMaster);
        }

        return storeMaster;
    }

    private  Map<String, BillingMasterBean> populateBillingMasterMap(){
        Map<String, BillingMasterBean> billingMasterBeanMap = new HashMap<>();

        List<BillingMasterBean> billingMasterBeans = billingMasterService.fetchCurrentBillingMasterBeans();

        for (BillingMasterBean billingMasterBean : billingMasterBeans) {
            billingMasterBeanMap.put(billingMasterBean.getUserId(), billingMasterBean);
        }

        return billingMasterBeanMap;
    }

    private StoreMaster populateStoreBuildings(List<FilteredSubscriptionBean> filteredSubscriptionBeans, StoreMaster storeMaster){
        Set<String> userSet = new HashSet<>();

        List<StoreBuilding> storeBuildings = new ArrayList<>();

        Map<String, BillingMasterBean> billingMasterBeanMap = populateBillingMasterMap();

        for(FilteredSubscriptionBean filteredSubscriptionBean : filteredSubscriptionBeans){
            String productAlias = filteredSubscriptionBean.getProductAlias();

            userSet.add(filteredSubscriptionBean.getUserId());

            StoreBuilding storeBuilding = new StoreBuilding();
            storeBuilding.setUserName(filteredSubscriptionBean.getUserName());
            storeBuilding.setBlock(filteredSubscriptionBean.getBlock());
            storeBuilding.setFlat(filteredSubscriptionBean.getFlat());

            Double productUnitPrice;

            if (filteredSubscriptionBean.getSubscriptionType() == 4 || filteredSubscriptionBean.getFlashSaleProduct()){
                productUnitPrice = filteredSubscriptionBean.getSubscriptionPrice();
            } else {
                productUnitPrice = filteredSubscriptionBean.getProductUnitPrice();
            }

            if(!StringUtils.isEmpty(productAlias)){
                storeBuilding.setProductName(productAlias);
            } else {
                storeBuilding.setProductName(filteredSubscriptionBean.getProductName());
            }

            storeBuilding.setProductQuantity(filteredSubscriptionBean.getProductQuantity());
            storeBuilding.setProductUnitSize(filteredSubscriptionBean.getProductUnitSize());
            storeBuilding.setProductUnitPrice(productUnitPrice);
            storeBuilding.setTotalAmount(filteredSubscriptionBean.getProductQuantity() * productUnitPrice);
            storeBuilding.setProductSubCategoryName(filteredSubscriptionBean.getProductSubCategoryName());
            storeBuilding.setFulfilledByPartner(filteredSubscriptionBean.getFulfilledByPartner());

            Double amount = billingMasterBeanMap.get(filteredSubscriptionBean.getUserId()).getAmount();

            if(amount != null){
                storeBuilding.setUserBalance(amount);
            }

            storeBuildings.add(storeBuilding);
        }

        //Update StoreMaster
        storeMaster.setUserCount(userSet.size());
        storeMaster.setStoreBuildings(storeBuildings);

        return populateStoreProductMap(storeBuildings, storeMaster);
    }

    private StoreMaster populateStoreProductMap(List<StoreBuilding> storeBuildings, StoreMaster storeMaster){
        Map<String, StoreProduct> storeProductMap = new HashMap<>();

        for(StoreBuilding storeBuilding : storeBuildings){
            StoreProduct storeProduct = new StoreProduct();

            String key = storeBuilding.getProductName().trim() + " " + storeBuilding.getProductUnitSize().trim();

            Double productUnitPrice = storeBuilding.getProductUnitPrice();

            if(storeProductMap.containsKey(key)){
                storeProduct = storeProductMap.get(key);
                storeProduct.setProductQuantity(storeProduct.getProductQuantity() + storeBuilding.getProductQuantity());
                storeProduct.setTotalAmount(storeProduct.getProductQuantity() * productUnitPrice);
                storeProductMap.put(key, storeProduct);
            } else {
                storeProduct.setProductName(storeBuilding.getProductName());
                storeProduct.setProductAlias(storeBuilding.getProductAlias());
                storeProduct.setProductUnitSize(storeBuilding.getProductUnitSize());
                storeProduct.setProductUnitPrice(storeBuilding.getProductUnitPrice());
                storeProduct.setProductQuantity(storeBuilding.getProductQuantity());
                storeProduct.setTotalAmount(storeProduct.getProductQuantity() * productUnitPrice);
                storeProduct.setProductSubCategoryName(storeBuilding.getProductSubCategoryName());
                storeProduct.setFulfilledByPartner(storeBuilding.getFulfilledByPartner());
                storeProductMap.put(key, storeProduct);
            }
        }

        return populateStoreProducts(storeProductMap, storeMaster);
    }

    private StoreMaster populateStoreProducts(Map<String, StoreProduct> storeProductMap, StoreMaster storeMaster){
        List<StoreProduct> storeProducts = new ArrayList<>();

        double totalAmountStore = 0;

        for(String key : storeProductMap.keySet()){
            StoreProduct storeProduct = storeProductMap.get(key);
            storeProducts.add(storeProduct);
            totalAmountStore = totalAmountStore + storeProduct.getTotalAmount();
        }

        //Update StoreMaster
        storeMaster.setStoreProducts(storeProducts);
        storeMaster.setTotalAmount(totalAmountStore);

        return storeMaster;
    }

    private void printStore(StoreMaster storeMaster){
        if (!CollectionUtils.isEmpty(storeMaster.getStoreBuildings()) && !CollectionUtils.isEmpty(storeMaster.getStoreProducts())){
            File directory = new File(Constants.STORE_PATH + DateOperations.getDateStringForStore(DateOperations.getTodayStartDate()));

            if (!directory.exists() && !directory.mkdirs()){
                errorLogger.error(TAG + "Error in creating directory : " + directory.getAbsolutePath());
                return;
            }

            generateBuildingsFile(storeMaster);

            generateProductsFile(storeMaster);
        }
    }

    private void generateBuildingsFile(StoreMaster storeMaster){
        List<StoreBuilding> storeBuildings = storeMaster.getStoreBuildings();

        // FIXME: 24-Nov-16 Update below line to make all CouponUsers fetched at the same time
        List<CouponUserBean> couponUserBeanList = couponUserService.findByBuilding(storeMaster.getBuildingId());

        Set<String> mergedBlockAndFlatSet = new HashSet<>();

        int k = 0;
        boolean removed = false;

        ComparatorChain comparatorChain = new ComparatorChain();
        comparatorChain.addComparator(new StoreBuilding().getFlatComparator());
        comparatorChain.addComparator(new StoreBuilding().getProductNameComparator());
        Collections.sort(storeBuildings, comparatorChain);

        for (Iterator<StoreBuilding> iterator = storeBuildings.iterator(); iterator.hasNext(); ) {
            StoreBuilding storeBuilding = iterator.next();

            mergedBlockAndFlatSet.add(storeBuilding.getMergedBlockAndFlat());

            if (k > 0) {
                if (storeBuildings.get(k - 1).getMergedBlockAndFlat().equals(storeBuildings.get(k).getMergedBlockAndFlat())
                        && storeBuildings.get(k - 1).getProductName().equals(storeBuildings.get(k).getProductName())) {
                    storeBuildings.get(k - 1).setProductQuantity(storeBuildings.get(k - 1).getProductQuantity() + storeBuildings.get(k).getProductQuantity());
                    iterator.remove();

                    removed = true;
                }
            }

            if (!removed) {
                k++;
            }

            removed = false;
        }

        for (CouponUserBean couponUserBean : couponUserBeanList) {
            if (!mergedBlockAndFlatSet.contains(couponUserBean.getMergedBlockAndFlat())) {
                storeBuildings.add(new StoreBuilding(couponUserBean.getBlock(), couponUserBean.getFlat()));
            }
        }

        Collections.sort(storeBuildings, new StoreBuilding().getFlatComparator());

        File file = new File(Constants.STORE_PATH + DateOperations.getDateStringForStore(DateOperations.getTodayStartDate()) + File.separator + storeMaster.getBuildingName() + ".xlsx");
        File newFile = new File(Constants.STORE_PATH + DateOperations.getDateStringForStore(DateOperations.getTodayStartDate()) + File.separator + storeMaster.getBuildingName() + "_New.xlsx");

        XSSFWorkbook xssfWorkbook = getXssfWorkbook(file);

        if (xssfWorkbook == null){
            return;
        }

        XSSFSheet xssfSheet = xssfWorkbook.createSheet("Building");
        xssfSheet.setDefaultRowHeight((short) 400);

        setFileHeaderForBuildingFile(xssfWorkbook, xssfSheet, storeMaster);

        initializeHeadersForWorksheet(xssfWorkbook, xssfSheet, getStoreBuildingPropertiesHeaderArray());

        int mergeCount = 0;
        boolean merge = false;

        for (int i = DATA_START_ROW + 1; i < storeBuildings.size() + DATA_START_ROW + 1; i++) {
            XSSFRow xssfRow = xssfSheet.createRow((short) i);

            String[] storeBuildingPropertiesArray = getStoreBuildingPropertiesArray(storeBuildings.get(i - DATA_START_ROW - 1));

            for (int j = 0; j < storeBuildingPropertiesArray.length; j++) {
                xssfSheet.setColumnWidth(j, getStoreBuildingCellWidthArray()[j]);
                Cell cell = xssfRow.createCell((short) j);

                if (j == 1 && !COUPONS.equals(storeBuildingPropertiesArray[j])) {
                    cell.setCellValue(getBuildingProductCellStyle(xssfWorkbook, storeBuildingPropertiesArray[j]));
                } else {
                    cell.setCellValue(storeBuildingPropertiesArray[j]);
                }
            }

            if (i > DATA_START_ROW + 1 && storeBuildings.get(i - DATA_START_ROW - 1).getFlat().equals(storeBuildings.get(i - DATA_START_ROW - 2).getFlat())){
                merge = true;
                mergeCount++;
            } else {
                if(merge){
                    mergeBuildingRows(xssfWorkbook, xssfSheet, i - 1 - mergeCount, i - 1, 0, storeBuildingPropertiesArray.length - 1);
                    merge = false;
                }

                mergeCount = 0;
            }

            if (i == storeBuildings.size() + DATA_START_ROW && merge){
                mergeBuildingRows(xssfWorkbook, xssfSheet, i - mergeCount, i, 0, storeBuildingPropertiesArray.length - 1);
            }
        }

        applyPrintSettings(xssfSheet, storeMaster);

        writeToWorkbook(file, newFile, xssfWorkbook, storeMaster.getBuildingName());
    }

    private void generateProductsFile(StoreMaster storeMaster){
        List<StoreProduct> storeProducts = storeMaster.getStoreProducts();

        Collections.sort(storeProducts, new StoreProduct().getProductNameComparator());

        File file = new File(Constants.STORE_PATH + DateOperations.getDateStringForStore(DateOperations.getTodayStartDate()) + File.separator + storeMaster.getBuildingName() + ".xlsx");
        File newFile = new File(Constants.STORE_PATH + DateOperations.getDateStringForStore(DateOperations.getTodayStartDate()) + File.separator + storeMaster.getBuildingName() + "_New.xlsx");

        XSSFWorkbook xssfWorkbook = getXssfWorkbook(file);

        if (xssfWorkbook == null){
            return;
        }

        XSSFSheet xssfSheet = xssfWorkbook.createSheet("Product");

        setFileHeaderForProductFile(xssfWorkbook, xssfSheet, storeMaster);

        initializeHeadersForWorksheet(xssfWorkbook, xssfSheet, getStoreProductPropertiesHeaderArray());

        for (int i = DATA_START_ROW + 1; i < storeProducts.size() + DATA_START_ROW + 1; i++) {
            XSSFRow xssfRow = xssfSheet.createRow((short) i);

            String[] storeProductPropertiesArray = getStoreProductPropertiesArray(storeProducts.get(i - DATA_START_ROW - 1));

            for (int j = 0; j < storeProductPropertiesArray.length; j++) {
                xssfSheet.setColumnWidth(j, getStoreProductCellWidthArray()[j]);
                Cell cell = xssfRow.createCell((short) j);
                cell.setCellValue(storeProductPropertiesArray[j]);
            }
        }

        applyPrintSettings(xssfSheet, storeMaster);

        writeToWorkbook(file, newFile, xssfWorkbook, storeMaster.getBuildingName());
    }

}