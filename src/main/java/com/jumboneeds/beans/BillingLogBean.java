package com.jumboneeds.beans;

import com.jumboneeds.utils.DateOperations;

import java.util.Date;

public class BillingLogBean {

    private String id;

    private Double amount;

    private Long date;

    private Double cashback;

    private String description;

    private Double previousBalance;

    private Integer billingLogType;

    private Integer paymentType;

    private String productImageUrl;

    public BillingLogBean() {

    }

    public BillingLogBean(String id, Double amount, Date date, Double cashback, String description, Double previousBalance, Integer billingLogType, Integer paymentType, String productImageUrl) {
        this.id = id;
        this.amount = amount;
        this.date = DateOperations.getServerToClientCustomStartDate(date, 0).getTime();
        this.cashback = cashback;
        this.description = description;
        this.previousBalance = previousBalance;
        this.billingLogType = billingLogType;
        this.paymentType = paymentType;
        this.productImageUrl = productImageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Double getCashback() {
        return cashback;
    }

    public void setCashback(Double cashback) {
        this.cashback = cashback;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(Double previousBalance) {
        this.previousBalance = previousBalance;
    }

    public Integer getBillingLogType() {
        return billingLogType;
    }

    public void setBillingLogType(Integer billingLogType) {
        this.billingLogType = billingLogType;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

}