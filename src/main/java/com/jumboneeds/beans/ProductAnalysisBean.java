package com.jumboneeds.beans;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ProductAnalysisBean {

    private String category;
    private String id;
    private BigDecimal quantity;
    private BigInteger userCount;
    private BigInteger orderCount;

    public ProductAnalysisBean(){

    }

    public ProductAnalysisBean(String category, String id, BigDecimal quantity, BigInteger userCount, BigInteger orderCount) {
        this.category = category;
        this.id = id;
        this.quantity = quantity;
        this.userCount = userCount;
        this.orderCount = orderCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigInteger getUserCount() {
        return userCount;
    }

    public void setUserCount(BigInteger userCount) {
        this.userCount = userCount;
    }

    public BigInteger getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(BigInteger orderCount) {
        this.orderCount = orderCount;
    }

}