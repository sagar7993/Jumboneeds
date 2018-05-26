package com.jumboneeds.beans;

import com.jumboneeds.utils.DateOperations;

import java.util.Date;

/**
 * Created by akash.mercer on 30-Dec-16.
 */
public class TransactionBean {

    private Double amount;

    private String bankTxnId;

    private Long paymentDate;

    private String paymentTypeName;

    private Integer paymentType;

    public TransactionBean(){

    }

    public TransactionBean(Double amount, String bankTxnId, Date paymentDate, String paymentTypeName, Integer paymentType) {
        this.amount = amount;
        this.bankTxnId = bankTxnId;
        this.paymentDate = DateOperations.getServerToClientCustomStartDate(paymentDate , 0).getTime();
        this.paymentTypeName = paymentTypeName;
        this.paymentType = paymentType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBankTxnId() {
        return bankTxnId;
    }

    public void setBankTxnId(String bankTxnId) {
        this.bankTxnId = bankTxnId;
    }

    public Long getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Long paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }
}
