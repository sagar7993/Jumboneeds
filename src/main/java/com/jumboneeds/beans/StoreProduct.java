package com.jumboneeds.beans;

import java.util.Comparator;

public class StoreProduct {

    private String productName;

    private String productAlias;

    private String productSubCategoryName;

    private String productUnitSize;

    private Double productUnitPrice;

    private Integer productQuantity;

    private Double totalAmount;

    private Boolean fulfilledByPartner;

    public StoreProduct(){

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

    public String getProductUnitSize() {
        return productUnitSize;
    }

    public void setProductUnitSize(String productUnitSize) {
        this.productUnitSize = productUnitSize;
    }

    public Double getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(Double productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
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

    Comparator<StoreProduct> productNameComparator = new Comparator<StoreProduct>() {
        @Override
        public int compare(StoreProduct lhs, StoreProduct rhs) {
            return lhs.getProductName().compareTo(rhs.productName);
        }
    };

    public Comparator<StoreProduct> getProductNameComparator() {
        return productNameComparator;
    }

    public void setProductNameComparator(Comparator<StoreProduct> productNameComparator) {
        this.productNameComparator = productNameComparator;
    }

}