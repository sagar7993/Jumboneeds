package com.jumboneeds.beans;

public class LoginBean {

    private String email;

    private String password;

    private String mobileNumber;

    private String accountType;

    private String deviceType;

    private String appVersion;

    private String fcmAndroidToken;

    public LoginBean(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        if (mobileNumber.length() > 10){
            return mobileNumber.substring(3, mobileNumber.length());
        } else {
            return mobileNumber;
        }
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getFcmAndroidToken() {
        return fcmAndroidToken;
    }

    public void setFcmAndroidToken(String fcmAndroidToken) {
        this.fcmAndroidToken = fcmAndroidToken;
    }

}