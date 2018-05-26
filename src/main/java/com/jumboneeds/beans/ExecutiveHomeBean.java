package com.jumboneeds.beans;

public class ExecutiveHomeBean extends StatusBean {

    private AppConfigBean appConfigBean;

    private String contactNumber;

    public ExecutiveHomeBean(){

    }

    public AppConfigBean getAppConfigBean() {
        return appConfigBean;
    }

    public void setAppConfigBean(AppConfigBean appConfigBean) {
        this.appConfigBean = appConfigBean;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

}