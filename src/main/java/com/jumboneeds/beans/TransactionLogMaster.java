package com.jumboneeds.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionLogMaster extends StatusBean {

    private List<TransactionLog> cashTransactions = new ArrayList<>();

    private List<TransactionLog> onlineTransactions = new ArrayList<>();

    private List<TransactionLog> couponRefundTransactions = new ArrayList<>();

    private List<TransactionLog> nonDeliveryRefundTransactions = new ArrayList<>();

    private List<TransactionLog> otherTransactions = new ArrayList<>();

    private List<TransactionLogAmountBean> transactionLogAmountBeans = new ArrayList<>();

    private HashMap<String, Double> amountMap;

    private String buildingName;

    private String date;

    public TransactionLogMaster(){

    }

    public TransactionLogMaster(List<TransactionLog> cashTransactions, List<TransactionLog> onlineTransactions, List<TransactionLog> couponRefundTransactions, List<TransactionLog> nonDeliveryRefundTransactions, List<TransactionLog> otherTransactions, List<TransactionLogAmountBean> transactionLogAmountBeans, HashMap<String, Double> amountMap, String buildingName, String date, Integer status) {
        this.cashTransactions = cashTransactions;
        this.onlineTransactions = onlineTransactions;
        this.couponRefundTransactions = couponRefundTransactions;
        this.nonDeliveryRefundTransactions = nonDeliveryRefundTransactions;
        this.otherTransactions = otherTransactions;
        this.transactionLogAmountBeans = transactionLogAmountBeans;
        this.amountMap = amountMap;
        this.buildingName = buildingName;
        this.date = date;
        this.status = status;
    }

    public List<TransactionLog> getCashTransactions() {
        return cashTransactions;
    }

    public void setCashTransactions(List<TransactionLog> cashTransactions) {
        this.cashTransactions = cashTransactions;
    }

    public List<TransactionLog> getOnlineTransactions() {
        return onlineTransactions;
    }

    public void setOnlineTransactions(List<TransactionLog> onlineTransactions) {
        this.onlineTransactions = onlineTransactions;
    }

    public List<TransactionLog> getCouponRefundTransactions() {
        return couponRefundTransactions;
    }

    public void setCouponRefundTransactions(List<TransactionLog> couponRefundTransactions) {
        this.couponRefundTransactions = couponRefundTransactions;
    }

    public List<TransactionLog> getNonDeliveryRefundTransactions() {
        return nonDeliveryRefundTransactions;
    }

    public void setNonDeliveryRefundTransactions(List<TransactionLog> nonDeliveryRefundTransactions) {
        this.nonDeliveryRefundTransactions = nonDeliveryRefundTransactions;
    }

    public List<TransactionLog> getOtherTransactions() {
        return otherTransactions;
    }

    public void setOtherTransactions(List<TransactionLog> otherTransactions) {
        this.otherTransactions = otherTransactions;
    }

    public List<TransactionLogAmountBean> getTransactionLogAmountBeans() {
        return transactionLogAmountBeans;
    }

    public void setTransactionLogAmountBeans(List<TransactionLogAmountBean> transactionLogAmountBeans) {
        this.transactionLogAmountBeans = transactionLogAmountBeans;
    }

    public HashMap<String, Double> getAmountMap() {
        return amountMap;
    }

    public void setAmountMap(HashMap<String, Double> amountMap) {
        this.amountMap = amountMap;
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

}