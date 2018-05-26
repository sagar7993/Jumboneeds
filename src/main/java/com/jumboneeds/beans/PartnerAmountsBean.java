package com.jumboneeds.beans;

public class PartnerAmountsBean {

    private String parentPartnerId;

    private String partnerName;

    private String date;

    private Double cashAmount;

    private Double couponRefundAmount;

    private Double nonDeliveryRefundAmount;

    private Double onlineAmount;

    private Double otherAmount;

    public PartnerAmountsBean() {

    }

    public PartnerAmountsBean(String parentPartnerId, String partnerName, String date, Double cashAmount, Double couponRefundAmount, Double nonDeliveryRefundAmount, Double onlineAmount, Double otherAmount) {
        this.parentPartnerId = parentPartnerId;
        this.partnerName = partnerName;
        this.date = date;
        this.cashAmount = cashAmount;
        this.couponRefundAmount = couponRefundAmount;
        this.nonDeliveryRefundAmount = nonDeliveryRefundAmount;
        this.onlineAmount = onlineAmount;
        this.otherAmount = otherAmount;
    }

    public String getParentPartnerId() {
        return parentPartnerId;
    }

    public void setParentPartnerId(String parentPartnerId) {
        this.parentPartnerId = parentPartnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Double getCouponRefundAmount() {
        return couponRefundAmount;
    }

    public void setCouponRefundAmount(Double couponRefundAmount) {
        this.couponRefundAmount = couponRefundAmount;
    }

    public Double getNonDeliveryRefundAmount() {
        return nonDeliveryRefundAmount;
    }

    public void setNonDeliveryRefundAmount(Double nonDeliveryRefundAmount) {
        this.nonDeliveryRefundAmount = nonDeliveryRefundAmount;
    }

    public Double getOnlineAmount() {
        return onlineAmount;
    }

    public void setOnlineAmount(Double onlineAmount) {
        this.onlineAmount = onlineAmount;
    }

    public Double getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(Double otherAmount) {
        this.otherAmount = otherAmount;
    }

}