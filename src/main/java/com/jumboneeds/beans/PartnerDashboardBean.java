package com.jumboneeds.beans;

public class PartnerDashboardBean extends StatusBean {

    private StoreMaster storeMaster;
    private AnalyticsMaster analyticsMaster;
    private InventoryMaster inventoryMaster;
    private TransactionLogMaster transactionLogMaster;
    private UserDetailBean userDetailBean;
    private PartnerDetailBean partnerDetailBean;
    private String partnerName;
    private String date;

    public StoreMaster getStoreMaster() {
        return storeMaster;
    }

    public void setStoreMaster(StoreMaster storeMaster) {
        this.storeMaster = storeMaster;
    }

    public AnalyticsMaster getAnalyticsMaster() {
        return analyticsMaster;
    }

    public void setAnalyticsMaster(AnalyticsMaster analyticsMaster) {
        this.analyticsMaster = analyticsMaster;
    }

    public InventoryMaster getInventoryMaster() {
        return inventoryMaster;
    }

    public void setInventoryMaster(InventoryMaster inventoryMaster) {
        this.inventoryMaster = inventoryMaster;
    }

    public TransactionLogMaster getTransactionLogMaster() {
        return transactionLogMaster;
    }

    public void setTransactionLogMaster(TransactionLogMaster transactionLogMaster) {
        this.transactionLogMaster = transactionLogMaster;
    }

    public UserDetailBean getUserDetailBean() {
        return userDetailBean;
    }

    public void setUserDetailBean(UserDetailBean userDetailBean) {
        this.userDetailBean = userDetailBean;
    }

    public PartnerDetailBean getPartnerDetailBean() {
        return partnerDetailBean;
    }

    public void setPartnerDetailBean(PartnerDetailBean partnerDetailBean) {
        this.partnerDetailBean = partnerDetailBean;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}