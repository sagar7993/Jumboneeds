package com.jumboneeds.beans;

import com.jumboneeds.entities.ProductSubCategory;

import java.math.BigInteger;

public class AddProductBean {

    private String productMasterId;
    private String productAlias;
    private Double productUnitPrice;
    private String productImage;
    private String productName;
    private String productUnitSize;
    private ProductSubCategory productSubCategory;
    private Boolean fulfilledByPartner;
    private String buildingIdList;
    private String partnerIdList;
    private Integer status;
    private Integer weight;
    private String productSubCategoryName;
    private String productCategoryName;
    private BigInteger buildingCount;
    private String fileName;
    private String specialText;

    public AddProductBean(){

    }

    public AddProductBean(String productMasterId, String productName, String productAlias, String productImage, String productUnitSize, Integer status, Integer weight, String productSubCategoryName, String productCategoryName, BigInteger buildingCount) {
        this.productMasterId = productMasterId;
        this.productName = productName;
        this.productAlias = productAlias;
        this.productImage = productImage;
        this.productUnitSize = productUnitSize;
        this.status = status;
        this.weight = weight;
        this.productSubCategoryName = productSubCategoryName;
        this.productCategoryName = productCategoryName;
        this.buildingCount = buildingCount;
    }

    public String getProductMasterId() { return productMasterId; }

    public void setProductMasterId(String productMasterId) { this.productMasterId = productMasterId; }

    public String getProductAlias() {
        return productAlias;
    }

    public void setProductAlias(String productAlias) {
        this.productAlias = productAlias;
    }

    public Double getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(Double productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
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

    public ProductSubCategory getProductSubCategory() {
        return productSubCategory;
    }

    public void setProductSubCategory(ProductSubCategory productSubCategory) {
        this.productSubCategory = productSubCategory;
    }

    public Boolean getFulfilledByPartner() {
        return fulfilledByPartner;
    }

    public void setFulfilledByPartner(Boolean fulfilledByPartner) {
        this.fulfilledByPartner = fulfilledByPartner;
    }

    public String getBuildingIdList() {
        return buildingIdList;
    }

    public void setBuildingIdList(String buildingIdList) {
        this.buildingIdList = buildingIdList;
    }

    public String getPartnerIdList() {
        return partnerIdList;
    }

    public void setPartnerIdList(String partnerIdList) {
        this.partnerIdList = partnerIdList;
    }

    public Integer getStatus() {
    	return status;
    }

    public void setStatus(Integer status) {
    	this.status = status;
    }

    public Integer getWeight() {
    	return weight;
	}

    public void setWeight(Integer weight) {
    	this.weight = weight;
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

    public BigInteger getBuildingCount() {
        return buildingCount;
    }

    public void setBuildingCount(BigInteger buildingCount) {
        this.buildingCount = buildingCount;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSpecialText() {
        return specialText;
    }

    public void setSpecialText(String specialText) {
        this.specialText = specialText;
    }

}