package com.jumboneeds.beans;

import java.util.List;

public class AnalyticsMaster extends StatusBean{

    private List<AnalyticsProduct> analyticsProducts;

    private List<AnalyticsCategory> analyticsCategories;

    private String buildingName;

    private String date;

    private Double totalAmount;

    private Integer userCount;

    private Integer orderCount;

    public AnalyticsMaster(){

    }

    public AnalyticsMaster(List<AnalyticsProduct> analyticsProducts, List<AnalyticsCategory> analyticsCategories, String buildingName, String date, Double totalAmount, Integer userCount, Integer orderCount, Integer status) {
        this.analyticsProducts = analyticsProducts;
        this.analyticsCategories = analyticsCategories;
        this.buildingName = buildingName;
        this.date = date;
        this.totalAmount = totalAmount;
        this.userCount = userCount;
        this.orderCount = orderCount;
        this.status = status;
    }

    public List<AnalyticsProduct> getAnalyticsProducts() {
        return analyticsProducts;
    }

    public void setAnalyticsProducts(List<AnalyticsProduct> analyticsProducts) {
        this.analyticsProducts = analyticsProducts;
    }

    public List<AnalyticsCategory> getAnalyticsCategories() {
        return analyticsCategories;
    }

    public void setAnalyticsCategories(List<AnalyticsCategory> analyticsCategories) {
        this.analyticsCategories = analyticsCategories;
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

}