package com.jumboneeds.beans;

import java.util.Comparator;

public class StoreBuilding {

    private String userName;

    private String block;

    private String flat;

    private String productName;

    private String productSubCategoryName;

    private String productAlias;

    private Integer productQuantity;

    private String productUnitSize;

    private Double productUnitPrice;

    private Double totalAmount;

    private Double userBalance;

    private Boolean fulfilledByPartner;

    public StoreBuilding() {

    }

    public StoreBuilding(String block, String flat) {
        this.block = block;
        this.flat = flat;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubCategoryName() {
        return productSubCategoryName;
    }

    public void setProductSubCategoryName(String productSubCategoryName) {
        this.productSubCategoryName = productSubCategoryName;
    }

    public String getProductAlias() {
        return productAlias;
    }

    public void setProductAlias(String productAlias) {
        this.productAlias = productAlias;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(Double userBalance) {
        this.userBalance = userBalance;
    }

    public Boolean getFulfilledByPartner() {
        return fulfilledByPartner;
    }

    public void setFulfilledByPartner(Boolean fulfilledByPartner) {
        this.fulfilledByPartner = fulfilledByPartner;
    }

    private Comparator<StoreBuilding> flatComparator = new Comparator<StoreBuilding>() {
        @Override
        public int compare(StoreBuilding lhs, StoreBuilding rhs) {
            String flatLeft = lhs.getMergedBlockAndFlat();
            String flatRight = rhs.getMergedBlockAndFlat();
            return flatRight.compareTo(flatLeft);
        }
    };

    private Comparator<StoreBuilding> productNameComparator = new Comparator<StoreBuilding>() {
        @Override
        public int compare(StoreBuilding lhs, StoreBuilding rhs) {
            String productNameLeft = lhs.getProductName();
            String productNameRight = rhs.getProductName();
            return productNameRight.compareTo(productNameLeft);
        }
    };

    public Comparator<StoreBuilding> getFlatComparator() {
        return flatComparator;
    }

    public Comparator<StoreBuilding> getProductNameComparator() {
        return productNameComparator;
    }

    public String getMergedBlockAndFlat(){
        return (getBlock() != null ? getBlock() + getFlat() : getFlat());
    }

}