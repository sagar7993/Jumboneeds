package com.jumboneeds.beans;

/**
 * Created by akash.mercer on 25-Oct-16.
 */
public class FlashSaleResponseBean extends StatusBean {

    private SubscriptionBean subscriptionBean;

    public FlashSaleResponseBean(){

    }

    public SubscriptionBean getSubscriptionBean() {
        return subscriptionBean;
    }

    public void setSubscriptionBean(SubscriptionBean subscriptionBean) {
        this.subscriptionBean = subscriptionBean;
    }
}
