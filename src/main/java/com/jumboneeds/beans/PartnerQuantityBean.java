package com.jumboneeds.beans;

import java.math.BigDecimal;

public class PartnerQuantityBean {

    private String parentPartnerId;

    private String partnerName;

    private String date;

    private BigDecimal milkQuantity;

    private BigDecimal nonMilkQuantity;

    public PartnerQuantityBean() {

    }

    public PartnerQuantityBean(String parentPartnerId, String partnerName, String date, BigDecimal milkQuantity, BigDecimal nonMilkQuantity) {
        this.parentPartnerId = parentPartnerId;
        this.partnerName = partnerName;
        this.date = date;
        this.milkQuantity = milkQuantity;
        this.nonMilkQuantity = nonMilkQuantity;
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