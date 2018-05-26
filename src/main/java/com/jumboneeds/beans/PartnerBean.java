package com.jumboneeds.beans;

public class PartnerBean {

    private String id;

    private String partnerName;

    private Double partnerCharges;

    private String userName;

    private Double balance;

    private String mobileNumber;

    private String profilePicUrl;

    public PartnerBean() {

    }

    public PartnerBean(String id, String partnerName, String mobileNumber, String profilePicUrl) {
        this.id = id;
        this.partnerName = partnerName;
        this.mobileNumber = mobileNumber;
        this.profilePicUrl = profilePicUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

}