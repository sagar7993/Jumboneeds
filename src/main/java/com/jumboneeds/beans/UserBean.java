package com.jumboneeds.beans;

import com.jumboneeds.entities.User;

import java.math.BigInteger;

public class UserBean {

    private String id;

    private String userName;

    private String email;

    private String profilePicUrl;

    private String flat;

    private String blockName;

    private String buildingName;

    private String buildingId;

    private String mobileNumber;

    private String billingMasterId;

    private Integer status;

    private Double balance;

    private BigInteger subscriptionCount;

    public UserBean(){

    }

    public UserBean(String id, String userName, String buildingName, String blockName, String flat, String email, String mobileNumber, Integer status, Double balance, BigInteger subscriptionCount) {
        this.id = id;
        this.userName = userName;
        this.buildingName = buildingName;
        this.blockName = blockName;
        this.flat = flat;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.status = status;
        this.balance = balance;
        this.subscriptionCount = subscriptionCount;
    }

    public UserBean(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.flat = user.getBlock() != null ? user.getBlock().getBlockName() + user.getFlat() : user.getFlat();
        this.buildingId = user.getBuilding().getId();
        this.buildingName = user.getBuilding().getBuildingName();
        this.mobileNumber = user.getMobileNumber();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getBillingMasterId() {
        return billingMasterId;
    }

    public void setBillingMasterId(String billingMasterId) {
        this.billingMasterId = billingMasterId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigInteger getSubscriptionCount() {
        return subscriptionCount;
    }

    public void setSubscriptionCount(BigInteger subscriptionCount) {
        this.subscriptionCount = subscriptionCount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}