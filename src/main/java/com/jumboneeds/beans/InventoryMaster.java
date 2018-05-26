package com.jumboneeds.beans;

import java.util.List;

public class InventoryMaster extends StatusBean{

    private List<InventoryProduct> inventoryProducts;

    private List<InventoryBuilding> inventoryBuildings;

    private String date;

    private Double totalAmount;

    private Integer userCount;

    private Integer orderCount;

    public InventoryMaster(){

    }

    public InventoryMaster(List<InventoryProduct> inventoryProducts, List<InventoryBuilding> inventoryBuildings, String date, Double totalAmount, Integer userCount, Integer orderCount, Integer status) {
        this.inventoryProducts = inventoryProducts;
        this.inventoryBuildings = inventoryBuildings;
        this.date = date;
        this.totalAmount = totalAmount;
        this.userCount = userCount;
        this.orderCount = orderCount;
        this.status = status;
    }

    public List<InventoryProduct> getInventoryProducts() {
        return inventoryProducts;
    }

    public void setInventoryProducts(List<InventoryProduct> inventoryProducts) {
        this.inventoryProducts = inventoryProducts;
    }

    public List<InventoryBuilding> getInventoryBuildings() {
        return inventoryBuildings;
    }

    public void setInventoryBuildings(List<InventoryBuilding> inventoryBuildings) {
        this.inventoryBuildings = inventoryBuildings;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

}