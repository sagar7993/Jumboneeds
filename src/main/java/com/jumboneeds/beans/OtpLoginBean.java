package com.jumboneeds.beans;

public class OtpLoginBean {

    private String mobileNumber;

    public OtpLoginBean(){

    }

    public String getMobileNumber() {
        return mobileNumber.substring(3, mobileNumber.length());
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}