package com.jumboneeds.beans;

import java.math.BigDecimal;

public class PartnerPaymentsBean {

    private String parentPartnerId;

    private String partnerName;

    private String date;

    private BigDecimal cashAmount;

    private BigDecimal couponRefundAmount;

    private BigDecimal nonDeliveryRefundAmount;

    private BigDecimal onlineAmount;

    private BigDecimal otherAmount;

    private BigDecimal totalAmount;

    private BigDecimal milkQuantity;

    private BigDecimal nonMilkQuantity;

    private Double milkCommission;

    public PartnerPaymentsBean() {

    }

    public PartnerPaymentsBean(String parentPartnerId, String partnerName, String date, BigDecimal cashAmount, BigDecimal couponRefundAmount, BigDecimal nonDeliveryRefundAmount, BigDecimal onlineAmount, BigDecimal otherAmount, BigDecimal milkQuantity, BigDecimal nonMilkQuantity) {
        this.parentPartnerId = parentPartnerId;
        this.partnerName = partnerName;
        this.date = date;
        this.cashAmount = cashAmount;
        this.couponRefundAmount = couponRefundAmount;
        this.nonDeliveryRefundAmount = nonDeliveryRefundAmount;
        this.onlineAmount = onlineAmount;
        this.otherAmount = otherAmount;
        this.milkQuantity = milkQuantity;
        this.nonMilkQuantity = nonMilkQuantity;
    }

    public PartnerPaymentsBean(PartnerAmountsBean partnerAmountsBean) {
        this.parentPartnerId = partnerAmountsBean.getParentPartnerId();
        this.partnerName = partnerAmountsBean.getPartnerName();
        this.date = partnerAmountsBean.getDate();
        this.cashAmount = BigDecimal.valueOf(partnerAmountsBean.getCashAmount());
        this.couponRefundAmount = BigDecimal.valueOf(partnerAmountsBean.getCouponRefundAmount());
        this.nonDeliveryRefundAmount = BigDecimal.valueOf(partnerAmountsBean.getNonDeliveryRefundAmount());
        this.onlineAmount = BigDecimal.valueOf(partnerAmountsBean.getOnlineAmount());
        this.otherAmount = BigDecimal.valueOf(partnerAmountsBean.getOtherAmount());
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

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getCouponRefundAmount() {
        return couponRefundAmount;
    }

    public void setCouponRefundAmount(BigDecimal couponRefundAmount) {
        this.couponRefundAmount = couponRefundAmount;
    }

    public BigDecimal getNonDeliveryRefundAmount() {
        return nonDeliveryRefundAmount;
    }

    public void setNonDeliveryRefundAmount(BigDecimal nonDeliveryRefundAmount) {
        this.nonDeliveryRefundAmount = nonDeliveryRefundAmount;
    }

    public BigDecimal getOnlineAmount() {
        return onlineAmount;
    }

    public void setOnlineAmount(BigDecimal onlineAmount) {
        this.onlineAmount = onlineAmount;
    }

    public BigDecimal getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(BigDecimal otherAmount) {
        this.otherAmount = otherAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getMilkQuantity() {
        return milkQuantity;
    }

    public void setMilkQuantity(BigDecimal milkQuantity) {
        this.milkQuantity = milkQuantity;
    }

    public BigDecimal getNonMilkQuantity() {
        return nonMilkQuantity;
    }

    public void setNonMilkQuantity(BigDecimal nonMilkQuantity) {
        this.nonMilkQuantity = nonMilkQuantity;
    }

}