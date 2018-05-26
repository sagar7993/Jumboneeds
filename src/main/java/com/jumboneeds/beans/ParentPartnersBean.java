package com.jumboneeds.beans;

public class ParentPartnersBean {

    private String id;

    private String partnerName;

    private String mobileNumber;

    public ParentPartnersBean(){

    }

    public ParentPartnersBean(String id, String partnerName, String mobileNumber){
        this.id = id;
        this.partnerName = partnerName;
        this.mobileNumber = mobileNumber;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}