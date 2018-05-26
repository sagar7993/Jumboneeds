package com.jumboneeds.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "app_config")
public class AppConfig {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="id")
    private String id;

    @Column(name = "android_version", nullable = false)
    private Integer androidVersion;

    @Column(name = "partner_app_version", nullable = false)
    private Integer partnerAppVersion;

    @Column(name = "executive_app_version", nullable = false)
    private Integer executiveAppVersion;

    @Column(name = "ios_version", nullable = false)
    private String iosVersion;

    @Column(name = "update_message")
    private String updateMessage;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public AppConfig(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}