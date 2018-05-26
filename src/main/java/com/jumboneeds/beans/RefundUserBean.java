package com.jumboneeds.beans;

public class RefundUserBean {

    private String id;

    private String userName;

    private String buildingName;

    private String blockName;

    private String flat;

    private String mobileNumber;

    private String email;

    private Double amount;

    public RefundUserBean(){

    }

    public RefundUserBean(String id, String userName, String buildingName, String blockName, String flat, String mobileNumber, String email, Double amount) {
        this.id = id;
        this.userName = userName;
        this.buildingName = buildingName;
        this.blockName = blockName;
        this.flat = flat;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmounte(Double amount) {
        this.amount = amount;
    }

}