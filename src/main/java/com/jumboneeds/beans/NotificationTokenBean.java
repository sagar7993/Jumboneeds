package com.jumboneeds.beans;

public class NotificationTokenBean {

    private String userId;

    private String token;

    public NotificationTokenBean(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}