package com.jumboneeds.beans;

public class ProductBuildingBean {

    private String id;

    private String productName;

    private String productUnitSize;

    private Double productUnitPrice;

    public ProductBuildingBean(){

    }

    public ProductBuildingBean(String id, String productName, String productUnitSize, Double productUnitPrice){
        this.id = id;
        this.productName = productName;
        this.productUnitSize = productUnitSize;
        this.productUnitPrice = productUnitPrice;
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

    public String getProductUnitSize() {
        return productUnitSize;
    }

    public void setProductUnitSize(String productUnitSize) {
        this.productUnitSize = productUnitSize;
    }

    public Double getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(Double productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

}