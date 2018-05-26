package com.jumboneeds.beans;

/**
 * Created by akash.mercer on 25-Nov-16.
 */
public class LowBalanceUserBean {

    private Double amount;

    private String fcmAndroidToken;

    public LowBalanceUserBean() {

    }

    public LowBalanceUserBean(Double amount, String fcmAndroidToken) {
        this.amount = amount;
        this.fcmAndroidToken = fcmAndroidToken;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getFcmAndroidToken() {
        return fcmAndroidToken;
    }

    public void setFcmAndroidToken(String fcmAndroidToken) {
        this.fcmAndroidToken = fcmAndroidToken;
    }
}
