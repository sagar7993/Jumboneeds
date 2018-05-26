package com.jumboneeds.beans;

public class StockBean {

    private String id;

    private String productName;

    private String unitSize;

    private Integer availableQuantity;

    private Integer averageOrders;

    private Integer totalOrder;

    private String stockStatus;

    private Integer stockType;

    public StockBean(){

    }

    public StockBean(String id, String productName, String unitSize, Integer availableQuantity, Integer averageOrders, Integer totalOrder, String stockStatus, Integer stockType) {
        this.id = id;
        this.productName = productName;
        this.unitSize = unitSize;
        this.availableQuantity = availableQuantity;
        this.averageOrders = averageOrders;
        this.totalOrder = totalOrder;
        this.stockStatus = stockStatus;
        this.stockType = stockType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnitSize() {
        return unitSize;
    }

    public void setUnitSize(String unitSize) {
        this.unitSize = unitSize;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Integer getAverageOrders() {
        return averageOrders;
    }

    public void setAverageOrders(Integer averageOrders) {
        this.averageOrders = averageOrders;
    }

    public Integer getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Integer totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

}