package com.jumboneeds.beans;

import com.jumboneeds.entities.Product;

import java.math.BigInteger;

public class ProductBean {

    private String id;

    private String productName;

    private String productImageUrl;

    private Double productUnitPrice;

    private String productUnitSize;

    private String productAlias;

    private String specialText;

    private String flashShortText;

    private Integer flashStatus;

    private Double strikePrice;

    private Integer status;

    private Boolean fulfilledByPartner;

    private Integer productQuantity;

    private String buildingName;

    private String address;

    private String productSubCategoryName;

    private String productCategoryName;

    private BigInteger count;

    public ProductBean(){

    }

    public ProductBean(String id, String productName, String productAlias, String productUnitSize) {
        this.id = id;
        this.productName = productName;
        this.productAlias = productAlias;
        this.productUnitSize = productUnitSize;
    }

    public ProductBean(String buildingId, String buildingName, String address, BigInteger count) {
        this.id = buildingId;
        this.buildingName = buildingName;
        this.address = address;
        this.count = count;
    }

    public ProductBean(String id, String productName, String productUnitSize, Double productUnitPrice, Double strikePrice, String specialText, String productSubCategoryName, String productCategoryName, String productImageUrl, BigInteger count) {
        this.id = id;
        this.productName = productName;
        this.productUnitSize = productUnitSize;
        this.productUnitPrice = productUnitPrice;
        this.strikePrice = strikePrice;
        this.specialText = specialText;
        this.productSubCategoryName = productSubCategoryName;
        this.productCategoryName = productCategoryName;
        this.productImageUrl = productImageUrl;
        this.count = count;
    }

    public ProductBean(String id, String productName, String productImageUrl, Double productUnitPrice, String productUnitSize, String productAlias, String specialText, Integer flashStatus, Double strikePrice) {
        this.id = id;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.productUnitPrice = productUnitPrice;
        this.productUnitSize = productUnitSize;
        this.productAlias = productAlias;
        this.specialText = specialText;
        this.flashStatus = flashStatus;
        this.strikePrice = strikePrice;
    }

    public ProductBean(Product product){
        id = product.getId();
        productName = product.getProductMaster().getProductName();
        productImageUrl = product.getProductMaster().getProductImageUrl();
        productUnitPrice = product.getProductUnitPrice();
        productUnitSize = product.getProductMaster().getProductUnitSize();
        productAlias = product.getProductMaster().getProductAlias();
        specialText = product.getSpecialText();
        flashStatus = product.getFlashStatus();
        strikePrice = product.getStrikePrice();
        status = product.getStatus();
        fulfilledByPartner = product.getFulfilledByPartner();
    }

    public ProductBean(String id, String productName, String productAlias, String productUnitSize, Double productUnitPrice, String buildingName, String productSubCategoryName, String productCategoryName, Boolean fulfilledByPartner, Integer status, String productImageUrl) {
        this.id = id;
        this.productName = productName;
        this.productAlias = productAlias;
        this.productUnitSize = productUnitSize;
        this.productUnitPrice = productUnitPrice;
        this.buildingName = buildingName;
        this.productSubCategoryName = productSubCategoryName;
        this.productCategoryName = productCategoryName;
        this.fulfilledByPartner = fulfilledByPartner;
        this.status = status;
        this.productImageUrl = productImageUrl;
    }

    public ProductBean(String id, String productName, String productAlias, String productUnitSize, Double productUnitPrice, Double strikePrice, String specialText, String buildingName, String productSubCategoryName, String productCategoryName, Boolean fulfilledByPartner, Integer status, String productImageUrl) {
        this.id = id;
        this.productName = productName;
        this.productAlias = productAlias;
        this.productUnitSize = productUnitSize;
        this.productUnitPrice = productUnitPrice;
        this.strikePrice = strikePrice;
        this.specialText = specialText;
        this.buildingName = buildingName;
        this.productSubCategoryName = productSubCategoryName;
        this.productCategoryName = productCategoryName;
        this.fulfilledByPartner = fulfilledByPartner;
        this.status = status;
        this.productImageUrl = productImageUrl;
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

    public Double getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(Double productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
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

    public String getSpecialText() {
        return specialText;
    }

    public void setSpecialText(String specialText) {
        this.specialText = specialText;
    }

    public String getFlashShortText() {
        return flashShortText;
    }

    public void setFlashShortText(String flashShortText) {
        this.flashShortText = flashShortText;
    }

    public Integer getFlashStatus() {
        return flashStatus;
    }

    public Boolean getFulfilledByPartner() {
        return fulfilledByPartner;
    }

    public void setFulfilledByPartner(Boolean fulfilledByPartner) {
        this.fulfilledByPartner = fulfilledByPartner;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public void setFlashStatus(Integer flashStatus) {
        this.flashStatus = flashStatus;
    }

    public Double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(Double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProductSubCategoryName() {
        return productSubCategoryName;
    }

    public void setProductSubCategoryName(String productSubCategoryName) {
        this.productSubCategoryName = productSubCategoryName;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }

}