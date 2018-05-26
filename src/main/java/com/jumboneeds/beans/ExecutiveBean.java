package com.jumboneeds.beans;

public class ExecutiveBean {

    private String id;

    private String executiveName;

    private String mobileNumber;

    private String profilePicUrl;

    public ExecutiveBean(){

    }

    public ExecutiveBean(String id, String executiveName, String mobileNumber, String profilePicUrl) {
        this.id = id;
        this.executiveName = executiveName;
        this.mobileNumber = mobileNumber;
        this.profilePicUrl = profilePicUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExecutiveName() {
        return executiveName;
    }

    public void setExecutiveName(String executiveName) {
        this.executiveName = executiveName;
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