package com.jumboneeds.beans;

public class BillingMasterBean {

    private String userId;

    private Double amount;

    private String bankTxnId;

    public BillingMasterBean(){

    }

    public BillingMasterBean(String userId, Double amount, String bankTxnId) {
        this.userId = userId;
        this.amount = amount;
        this.bankTxnId = bankTxnId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBankTxnId() {
        return bankTxnId;
    }

    public void setBankTxnId(String bankTxnId) {
        this.bankTxnId = bankTxnId;
    }
}