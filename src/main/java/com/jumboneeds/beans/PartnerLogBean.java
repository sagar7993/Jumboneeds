package com.jumboneeds.beans;

import java.util.Date;

public class PartnerLogBean {

    private String id;

    private Double onlineAmount;

    private Double serviceAmount;

    private Double ninjaAmount;

    private Double happayAmount;

    private Double adjustmentAmount;

    private Double partnerBalance;

    private Double previousBalance;

    private String remarks;

    private String buildingName;

    private Long date;

    public PartnerLogBean(){

    }

    public PartnerLogBean(String id, Double onlineAmount, Double serviceAmount, Double ninjaAmount, Double happayAmount, Double adjustmentAmount, Double partnerBalance, Double previousBalance, String remarks, String buildingName, Date date) {
        this.id = id;
        this.onlineAmount = onlineAmount;
        this.serviceAmount = serviceAmount;
        this.ninjaAmount = ninjaAmount;
        this.happayAmount = happayAmount;
        this.adjustmentAmount = adjustmentAmount;
        this.partnerBalance = partnerBalance;
        this.previousBalance = previousBalance;
        this.remarks = remarks;
        this.buildingName = buildingName;
        this.date = date.getTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getOnlineAmount() {
        return onlineAmount;
    }

    public void setOnlineAmount(Double onlineAmount) {
        this.onlineAmount = onlineAmount;
    }

    public Double getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(Double serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public Double getNinjaAmount() {
        return ninjaAmount;
    }

    public void setNinjaAmount(Double ninjaAmount) {
        this.ninjaAmount = ninjaAmount;
    }

    public Double getHappayAmount() {
        return happayAmount;
    }

    public void setHappayAmount(Double happayAmount) {
        this.happayAmount = happayAmount;
    }

    public Double getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public void setAdjustmentAmount(Double adjustmentAmount) {
        this.adjustmentAmount = adjustmentAmount;
    }

    public Double getPartnerBalance() {
        return partnerBalance;
    }

    public void setPartnerBalance(Double partnerBalance) {
        this.partnerBalance = partnerBalance;
    }

    public Double getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(Double previousBalance) {
        this.previousBalance = previousBalance;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

}