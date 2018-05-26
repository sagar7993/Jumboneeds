package com.jumboneeds.beans;

import java.math.BigDecimal;
import java.math.BigInteger;

public class UserAnalysisBean {

    private String userId;
    private String userName;
    private String buildingName;
    private String blockName;
    private String flat;
    private String mobileNumber;
    private BigDecimal quantity;
    private BigInteger orderCount;
    private Double revenue;

    public UserAnalysisBean(){

    }

    public UserAnalysisBean(String userId, String userName, String buildingName, String blockName, String flat, String mobileNumber, BigDecimal quantity, BigInteger orderCount, Double revenue) {
        this.userId = userId;
        this.userName = userName;
        this.buildingName = buildingName;
        this.blockName = blockName;
        this.flat = flat;
        this.mobileNumber = mobileNumber;
        this.quantity = quantity;
        this.orderCount = orderCount;
        this.revenue = revenue;
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

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigInteger getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(BigInteger orderCount) {
        this.orderCount = orderCount;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

}