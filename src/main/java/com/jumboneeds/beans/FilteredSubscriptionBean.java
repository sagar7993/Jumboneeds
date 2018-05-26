package com.jumboneeds.beans;

import java.util.Date;

/**
 * Created by akash.mercer on 14-Oct-16.
 */
public class FilteredSubscriptionBean {

    private String id;

    private Date startDate;

    private Date endDate;

    private String productId;

    private String productName;

    private String productAlias;

    private String productSubCategoryName;

    private Integer productSubCategoryType;

    private String productCategoryName;

    private Integer productCategoryType;

    private String productUnitSize;

    private String productImageUrl;

    private Double productUnitPrice;

    private Double subscriptionPrice;

    private Integer productQuantity;

    private Double totalAmount;

    private Boolean fulfilledByPartner;

    private Integer subscriptionType;

    private Boolean flashSaleProduct;

    private String userId;

    private String userName;

    private String block;

    private String flat;

    private String buildingName;

    public FilteredSubscriptionBean(){

    }

    public FilteredSubscriptionBean(String id, Date startDate, Date endDate, String productId, String productName, String productAlias, String productSubCategoryName, Integer productSubCategoryType, String productCategoryName, Integer productCategoryType, String productUnitSize, String productImageUrl, Double productUnitPrice, Double subscriptionPrice, Integer productQuantity, Boolean fulfilledByPartner, Integer subscriptionType, Boolean flashSaleProduct, String userId, String userName, String block, String flat, String buildingName) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.productId = productId;
        this.productName = productName;
        this.productAlias = productAlias;
        this.productSubCategoryName = productSubCategoryName;
        this.productSubCategoryType = productSubCategoryType;
        this.productCategoryName = productCategoryName;
        this.productCategoryType = productCategoryType;
        this.productUnitSize = productUnitSize;
        this.productImageUrl = productImageUrl;
        this.productUnitPrice = productUnitPrice;
        this.subscriptionPrice = subscriptionPrice;
        this.productQuantity = productQuantity;
        this.fulfilledByPartner = fulfilledByPartner;
        this.subscriptionType = subscriptionType;
        this.flashSaleProduct = flashSaleProduct;
        this.userId = userId;
        this.userName = userName;
        this.block = block;
        this.flat = flat;
        this.buildingName = buildingName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAlias() {
        return productAlias;
    }

    public void setProductAlias(String productAlias) {
        this.productAlias = productAlias;
    }

    public String getProductSubCategoryName() {
        return productSubCategoryName;
    }

    public void setProductSubCategoryName(String productSubCategoryName) {
        this.productSubCategoryName = productSubCategoryName;
    }

    public Integer getProductSubCategoryType() {
        return productSubCategoryType;
    }

    public void setProductSubCategoryType(Integer productSubCategoryType) {
        this.productSubCategoryType = productSubCategoryType;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Integer getProductCategoryType() {
        return productCategoryType;
    }

    public void setProductCategoryType(Integer productCategoryType) {
        this.productCategoryType = productCategoryType;
    }

    public String getProductUnitSize() {
        return productUnitSize;
    }

    public void setProductUnitSize(String productUnitSize) {
        this.productUnitSize = productUnitSize;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public Double getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(Double productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public Double getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(Double subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getFulfilledByPartner() {
        return fulfilledByPartner;
    }

    public void setFulfilledByPartner(Boolean fulfilledByPartner) {
        this.fulfilledByPartner = fulfilledByPartner;
    }

    public Integer getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(Integer subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Boolean getFlashSaleProduct() {
        return flashSaleProduct;
    }

    public void setFlashSaleProduct(Boolean flashSaleProduct) {
        this.flashSaleProduct = flashSaleProduct;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}
