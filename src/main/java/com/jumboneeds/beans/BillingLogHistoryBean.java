package com.jumboneeds.beans;

import java.util.ArrayList;
import java.util.List;

public class BillingLogHistoryBean extends StatusBean{

    private List<BillingLogBean> billingLogBeans = new ArrayList<>();

    public BillingLogHistoryBean(){

    }

    public List<BillingLogBean> getBillingLogBeans() {
        return billingLogBeans;
    }

    public void setBillingLogBeans(List<BillingLogBean> billingLogBeens) {
        this.billingLogBeans = billingLogBeens;
    }

}