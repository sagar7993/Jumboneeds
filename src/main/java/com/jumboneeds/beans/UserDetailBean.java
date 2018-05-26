package com.jumboneeds.beans;

import java.util.List;

public class UserDetailBean extends StatusBean {

    public UserDetailBean() {

    }

    private String title;

    private List<UserBean> activeUserBeans;

    private List<UserBean> inActiveUserBeans;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<UserBean> getActiveUserBeans() {
        return activeUserBeans;
    }

    public void setActiveUserBeans(List<UserBean> activeUserBeans) {
        this.activeUserBeans = activeUserBeans;
    }

    public List<UserBean> getInActiveUserBeans() {
        return inActiveUserBeans;
    }

    public void setInActiveUserBeans(List<UserBean> inActiveUserBeans) {
        this.inActiveUserBeans = inActiveUserBeans;
    }

}