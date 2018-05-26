package com.jumboneeds.beans;

public class CreManagerBean {

    private String id;

    private String flat;

    private String reason;

    public CreManagerBean(){

    }

    public CreManagerBean(String id, String flat, String reason){
        this.id = id;
        this.flat = flat;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}