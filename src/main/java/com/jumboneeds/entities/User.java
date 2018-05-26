package com.jumboneeds.entities;

import com.jumboneeds.beans.PartnerAmountsBean;
import com.jumboneeds.beans.PartnerQuantityBean;
import com.jumboneeds.beans.RefundUserBean;
import com.jumboneeds.beans.UserBean;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")

@SqlResultSetMappings({
    @SqlResultSetMapping(name="RefundUserBean", classes = { @ConstructorResult(targetClass = RefundUserBean.class,
        columns = {@ColumnResult(name="id"), @ColumnResult(name="user_name"), @ColumnResult(name="building_name"), @ColumnResult(name="block_name"),
            @ColumnResult(name="flat"), @ColumnResult(name="mobile_number"), @ColumnResult(name="email"), @ColumnResult(name="amount")
        })
    }),
    @SqlResultSetMapping(name="UserDetailsList", classes = { @ConstructorResult(targetClass = UserBean.class,
        columns = {@ColumnResult(name="id"), @ColumnResult(name="user_name"), @ColumnResult(name="building_name"),
            @ColumnResult(name="block_name"), @ColumnResult(name="flat"), @ColumnResult(name="email"), @ColumnResult(name="mobile_number"),
            @ColumnResult(name="status"), @ColumnResult(name="amount"), @ColumnResult(name="subscription_count")
        })
    })
})

public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="id")
    private String id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin")
    private String email;

    @Column(name = "profile_pic_url")
    private String profilePicUrl;

    @Column(name = "flat")
    private String flat;

    @Column(name = "account_type", nullable = false)
    private String accountType;

    @Column(name = "mobile_number", nullable = false, unique = true)
    private String mobileNumber;

    @Column(name = "device_type", nullable = false)
    private String deviceType;

    @Column(name = "fcm_android_token")
    private String fcmAndroidToken;

    @Column(name = "app_version", nullable = false)
    private String appVersion;

    @Column(name = "milk_charge", columnDefinition = "double default '1'")
    private Double milkCharge = 1.0;

    @Column(name = "status", columnDefinition = "integer default 1")
    private Integer status = 1;

    @Column(name = "billing_master_id")
    private String billingMasterId;

    @Transient
    private Double amount;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "building")
    private Building building;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "block")
    private Block block;

    public User() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getMobileNumber() {
        if (mobileNumber.length() > 10){
            mobileNumber = mobileNumber.substring(3, mobileNumber.length());
            return mobileNumber;
        } else {
            return mobileNumber;
        }
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getFcmAndroidToken() {
        return fcmAndroidToken;
    }

    public void setFcmAndroidToken(String fcmAndroidToken) {
        this.fcmAndroidToken = fcmAndroidToken;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Double getMilkCharge() {
        return milkCharge;
    }

    public void setMilkCharge(Double milkCharge) {
        this.milkCharge = milkCharge;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBillingMasterId() {
        return billingMasterId;
    }

    public void setBillingMasterId(String billingMasterId) {
        this.billingMasterId = billingMasterId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}