package com.jumboneeds.beans;

import java.util.ArrayList;
import java.util.List;

public class ScheduleBean extends StatusBean{

    private Integer pastLimit;

    private Integer futureLimit;

    private List<SubscriptionBean> subscriptionBeans = new ArrayList<>();

    private List<SubscriptionExceptionBean> subscriptionExceptionBeans = new ArrayList<>();

    private List<BillingLogBean> billingLogBeans = new ArrayList<>();

    public ScheduleBean(){

    }

    public Integer getPastLimit() {
        return pastLimit;
    }

    public void setPastLimit(Integer pastLimit) {
        this.pastLimit = pastLimit;
    }

    public Integer getFutureLimit() {
        return futureLimit;
    }

    public void setFutureLimit(Integer futureLimit) {
        this.futureLimit = futureLimit;
    }

    public List<SubscriptionBean> getSubscriptionBeans() {
        return subscriptionBeans;
    }

    public void setSubscriptionBeans(List<SubscriptionBean> subscriptionBeans) {
        this.subscriptionBeans = subscriptionBeans;
    }

    public List<SubscriptionExceptionBean> getSubscriptionExceptionBeans() {
        return subscriptionExceptionBeans;
    }

    public void setSubscriptionExceptionBeans(List<SubscriptionExceptionBean> subscriptionExceptionBeans) {
        this.subscriptionExceptionBeans = subscriptionExceptionBeans;
    }

    public List<BillingLogBean> getBillingLogBeans() {
        return billingLogBeans;
    }

    public void setBillingLogBeans(List<BillingLogBean> billingLogBeans) {
        this.billingLogBeans = billingLogBeans;
    }
}