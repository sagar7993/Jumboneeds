package com.jumboneeds.beans;

import com.jumboneeds.utils.DateOperations;

import java.util.Date;

public class SubscriptionExceptionBean {

    private String id;

    private Long date;

    private Integer productQuantity;

    private String subscriptionId;

    public SubscriptionExceptionBean(){

    }

    public SubscriptionExceptionBean(String id, Date date, Integer productQuantity, String subscriptionId) {
        this.id = id;
        this.date = DateOperations.getServerToClientCustomStartDate(date, 0).getTime();
        this.productQuantity = productQuantity;
        this.subscriptionId = subscriptionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

}