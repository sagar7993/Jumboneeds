package com.jumboneeds.beans;

public class SubscriptionExceptionUpdateBean extends StatusBean{

    private String subscriptionExceptionId;

    private Integer productQuantity;

    public SubscriptionExceptionUpdateBean(){

    }

    public String getSubscriptionExceptionId() {
        return subscriptionExceptionId;
    }

    public void setSubscriptionExceptionId(String subscriptionExceptionId) {
        this.subscriptionExceptionId = subscriptionExceptionId;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

}