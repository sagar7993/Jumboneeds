package com.jumboneeds.beans;

public class BillingLogUserBean {

    private String id;

    private String flat;

    private Double walletBalance;

    public BillingLogUserBean(){

    }

    public BillingLogUserBean(String id, String flat, Double walletBalance) {
        this.id = id;
        this.flat = flat;
        this.walletBalance = walletBalance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public Double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Double walletBalance) {
        this.walletBalance = walletBalance;
    }

}