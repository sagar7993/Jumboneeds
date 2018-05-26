package com.jumboneeds.beans;

import com.jumboneeds.entities.User;

public class LoginResultBean extends StatusBean{

    private String userId;

    private String userName;

    private String email;

    private String mobileNumber;

    private String profilePicUrl;

    private Integer userStatus;

    public LoginResultBean(){

    }

    public LoginResultBean(User user){
        this.userId = user.getId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.mobileNumber = user.getMobileNumber();
        this.profilePicUrl = user.getProfilePicUrl();
        this.userStatus = user.getStatus();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

}