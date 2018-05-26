package com.jumboneeds.beans;

public class WalletBalanceBean extends StatusBean{

    private BillingMasterBean billingMasterBean;

    public WalletBalanceBean(){

    }

    public BillingMasterBean getBillingMasterBean() {
        return billingMasterBean;
    }

    public void setBillingMasterBean(BillingMasterBean billingMasterBean) {
        this.billingMasterBean = billingMasterBean;
    }

}