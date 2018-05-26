package com.jumboneeds.beans;

public class AnalyticsCategory {

    private Integer productCategoryType;

    private Integer productSubCategoryType;

    private String productCategoryName;

    private String productSubCategoryName;

    private Integer productQuantity;

    private Double totalAmount;

    private Boolean fulfilledByPartner;

    public AnalyticsCategory(){

    }

    public Integer getProductCategoryType() {
        return productCategoryType;
    }

    public void setProductCategoryType(Integer productCategoryType) {
        this.productCategoryType = productCategoryType;
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

    public String getProductSubCategoryName() {
        return productSubCategoryName;
    }

    public void setProductSubCategoryName(String productSubCategoryName) {
        this.productSubCategoryName = productSubCategoryName;
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

}