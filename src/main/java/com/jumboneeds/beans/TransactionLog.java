package com.jumboneeds.beans;

public class TransactionLog {

    private String userName;

    private String buildingName;

    private String blockName;

    private String flat;

    private Double amount;

    private Double cashback;

    private String bankTxnId;

    private String description;

	public TransactionLog(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBuildingName() { return  buildingName; }

    public void setBuildingName(String buildingName) { this.buildingName = buildingName; }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getCashback() {
        return cashback;
    }

    public void setCashback(Double cashback) {
        this.cashback = cashback;
    }

    public String getBankTxnId() {
        return bankTxnId;
    }

    public void setBankTxnId(String bankTxnId) {
        this.bankTxnId = bankTxnId;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

}