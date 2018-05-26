package com.jumboneeds.beans;

import com.jumboneeds.entities.BillingMaster;
import com.jumboneeds.entities.Executive;
import com.jumboneeds.entities.Partner;
import com.jumboneeds.entities.User;

public class PaymentBean {

    private String userId;

    private String mobileNumber;

    private Double amount;

    private String txnId;

    private String bankTxnId;

    private Double cashback = 0.0;

    private Double milkAmount = 0.0;

    private Double nonMilkAmount = 0.0;

    private Integer paymentType;

    private String description;

    private String partnerId;

    private String executiveId;

    private User user;

    private BillingMaster billingMaster;

    private Partner partner;

    private Executive executive;

    public PaymentBean(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getBankTxnId() {
        return bankTxnId;
    }

    public void setBankTxnId(String bankTxnId) {
        this.bankTxnId = bankTxnId;
    }

    public Double getCashback() {
        return cashback;
    }

    public void setCashback(Double cashback) {
        this.cashback = cashback;
    }

    public Double getMilkAmount() {
        return milkAmount;
    }

    public void setMilkAmount(Double milkAmount) {
        this.milkAmount = milkAmount;
    }

    public Double getNonMilkAmount() {
        return nonMilkAmount;
    }

    public void setNonMilkAmount(Double nonMilkAmount) {
        this.nonMilkAmount = nonMilkAmount;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getExecutiveId() {
        return executiveId;
    }

    public void setExecutiveId(String executiveId) {
        this.executiveId = executiveId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BillingMaster getBillingMaster() {
        return billingMaster;
    }

    public void setBillingMaster(BillingMaster billingMaster) {
        this.billingMaster = billingMaster;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Executive getExecutive() {
        return executive;
    }

    public void setExecutive(Executive executive) {
        this.executive = executive;
    }

}