package com.jumboneeds.beans;

import java.math.BigInteger;
import java.util.List;

public class PartnerDetailBean {

    private String partnerName;
    private String mobileNumber;
    private Double partnerCharges;
    private String userName;
    private String password;
    private String buildingId;
    private BigInteger buildingCount;
    private List<AddBuildingBean> buildingBeanList;

    public PartnerDetailBean() {

    }

    public PartnerDetailBean(String partnerName, String mobileNumber, Double partnerCharges, String userName, String password, String buildingId, BigInteger buildingCount) {
        this.partnerName = partnerName;
        this.mobileNumber = mobileNumber;
        this.partnerCharges = partnerCharges;
        this.userName = userName;
        this.password = password;
        this.buildingId = buildingId;
        this.buildingCount = buildingCount;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Double getPartnerCharges() {
        return partnerCharges;
    }

    public void setPartnerCharges(Double partnerCharges) {
        this.partnerCharges = partnerCharges;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public BigInteger getBuildingCount() {
        return buildingCount;
    }

    public void setBuildingCount(BigInteger buildingCount) {
        this.buildingCount = buildingCount;
    }

    public List<AddBuildingBean> getBuildingBeanList() {
        return buildingBeanList;
    }

    public void setBuildingBeanList(List<AddBuildingBean> buildingBeanList) {
        this.buildingBeanList = buildingBeanList;
    }

}