package com.jumboneeds.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 30-Dec-16.
 */
public class TransactionHistoryBean extends StatusBean {

    private List<TransactionBean> transactions = new ArrayList<>();

    public TransactionHistoryBean() {

    }

    public List<TransactionBean> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionBean> transactions) {
        this.transactions = transactions;
    }

}
