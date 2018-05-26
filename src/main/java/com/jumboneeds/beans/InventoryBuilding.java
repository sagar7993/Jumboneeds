package com.jumboneeds.beans;

import java.util.Comparator;

public class InventoryBuilding {

    private String userName;

    private String buildingName;

    private String block;

    private String flat;

    private Integer productCategoryType;

    private Integer productSubCategoryType;

    private String productCategoryName;

    private String productSubCategoryName;

    private String productName;

    private String productUnitSize;

    private Double productUnitPrice;

    private Integer productQuantity;

    private Double totalAmount;

    public InventoryBuilding(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getMergedBlockAndFlat(){
        return (getBlock() != null ? getBlock() : "") + getFlat();
    }

    public Comparator<InventoryBuilding> buildingComparator = new Comparator<InventoryBuilding>() {
        @Override
        public int compare(InventoryBuilding lhs, InventoryBuilding rhs) {
            String buildingLeft = lhs.getBuildingName();
            String buildingRight = rhs.getBuildingName();
            return buildingLeft.compareTo(buildingRight);
        }
    };

    public Comparator<InventoryBuilding> mergedBlockAndFlatComparator = new Comparator<InventoryBuilding>() {
        @Override
        public int compare(InventoryBuilding lhs, InventoryBuilding rhs) {
            String mergedBlockAndFlatLeft = lhs.getMergedBlockAndFlat();
            String mergedBlockAndFlatRight = rhs.getMergedBlockAndFlat();
            return mergedBlockAndFlatRight.compareTo(mergedBlockAndFlatLeft);
        }
    };

    public Comparator<InventoryBuilding> getBuildingComparator() {
        return buildingComparator;
    }

    public Comparator<InventoryBuilding> getMergedBlockAndFlatComparator() {
        return mergedBlockAndFlatComparator;
    }
}