package com.jumboneeds.beans;

import java.util.List;

public class StoreMaster extends StatusBean {

    private List<StoreBuilding> storeBuildings;

    private List<StoreProduct> storeProducts;

    private String buildingName;

    private String date;

    private Double totalAmount;

    private Integer userCount;

    private Integer orderCount;

    private String buildingId;

    public StoreMaster(){

    }

    public StoreMaster(List<StoreBuilding> storeBuildings, List<StoreProduct> storeProducts, String buildingName, String date, Double totalAmount, Integer userCount, Integer orderCount, String buildingId, Integer status) {
        this.storeBuildings = storeBuildings;
        this.storeProducts = storeProducts;
        this.buildingName = buildingName;
        this.date = date;
        this.totalAmount = totalAmount;
        this.userCount = userCount;
        this.orderCount = orderCount;
        this.buildingId = buildingId;
        this.status = status;
    }

    public List<StoreBuilding> getStoreBuildings() {
        return storeBuildings;
    }

    public void setStoreBuildings(List<StoreBuilding> storeBuildings) {
        this.storeBuildings = storeBuildings;
    }

    public List<StoreProduct> getStoreProducts() {
        return storeProducts;
    }

    public void setStoreProducts(List<StoreProduct> storeProducts) {
        this.storeProducts = storeProducts;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }
}