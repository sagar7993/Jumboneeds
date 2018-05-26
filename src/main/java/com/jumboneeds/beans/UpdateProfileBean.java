package com.jumboneeds.beans;

import com.jumboneeds.entities.User;

public class UpdateProfileBean {

    private String userId;

    private String userName;

    private String mobileNumber;

    private String email;

    private String buildingId;

    private String flat;

    private String password;

    public UpdateProfileBean(){

    }

    public UpdateProfileBean(User user) {
        this.userId = user.getId();
        this.userName = user.getUserName();
        this.mobileNumber = user.getMobileNumber();
        this.email = user.getEmail();
        this.flat = user.getFlat();
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}