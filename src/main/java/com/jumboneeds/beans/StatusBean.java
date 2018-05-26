package com.jumboneeds.beans;

public class StatusBean {

    public Integer status = 0;

    public String message;

    public StatusBean(){

    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}