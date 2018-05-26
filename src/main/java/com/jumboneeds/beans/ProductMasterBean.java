package com.jumboneeds.beans;

/**
 * Created by akash.mercer on 25-Nov-16.
 */
public class ProductMasterBean {

    private String id;

    private String productName;

    private String productImageUrl;

    private String productUnitSize;

    private String productAlias;

    private Integer status;

    public ProductMasterBean() {

    }

    public ProductMasterBean(String id, String productName, String productImageUrl, String productUnitSize, String productAlias) {
        this.id = id;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.productUnitSize = productUnitSize;
        this.productAlias = productAlias;
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

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductUnitSize() {
        return productUnitSize;
    }

    public void setProductUnitSize(String productUnitSize) {
        this.productUnitSize = productUnitSize;
    }

    public String getProductAlias() {
        return productAlias;
    }

    public void setProductAlias(String productAlias) {
        this.productAlias = productAlias;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
