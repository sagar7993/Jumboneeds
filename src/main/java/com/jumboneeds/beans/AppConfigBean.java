package com.jumboneeds.beans;

import com.jumboneeds.entities.AppConfig;

public class AppConfigBean {

    private Integer androidVersion;

    private Integer partnerAppVersion;

    private Integer executiveAppVersion;

    private String iosVersion;

    private String updateMessage;

    public AppConfigBean(){

    }

    public AppConfigBean(AppConfig appConfig) {
        if (appConfig != null) {
            androidVersion = appConfig.getAndroidVersion();
            partnerAppVersion = appConfig.getPartnerAppVersion();
            executiveAppVersion = appConfig.getExecutiveAppVersion();
            iosVersion = appConfig.getIosVersion();
            updateMessage = appConfig.getUpdateMessage();
        }
    }

    public Integer getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(Integer androidVersion) {
        this.androidVersion = androidVersion;
    }

    public Integer getPartnerAppVersion() {
        return partnerAppVersion;
    }

    public void setPartnerAppVersion(Integer partnerAppVersion) {
        this.partnerAppVersion = partnerAppVersion;
    }

    public Integer getExecutiveAppVersion() {
        return executiveAppVersion;
    }

    public void setExecutiveAppVersion(Integer executiveAppVersion) {
        this.executiveAppVersion = executiveAppVersion;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }
}