package com.jumboneeds.beans;

import java.util.ArrayList;
import java.util.List;

public class ProductsBean extends StatusBean{

    private List<ProductSubCategoryBean> productSubCategoryBeans = new ArrayList<>();

    private List<ProductBean> productBeans = new ArrayList<>();

    private String productName;

    private String productAlias;

    private String buildingName;

    public ProductsBean(){

    }

    public List<ProductSubCategoryBean> getProductSubCategoryBeans() {
        return productSubCategoryBeans;
    }

    public void setProductSubCategoryBeans(List<ProductSubCategoryBean> productSubCategoryBeans) {
        this.productSubCategoryBeans = productSubCategoryBeans;
    }

    public List<ProductBean> getProductBeans() {
        return productBeans;
    }

    public void setProductBeans(List<ProductBean> productBeans) {
        this.productBeans = productBeans;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAlias() {
        return productAlias;
    }

    public void setProductAlias(String productAlias) {
        this.productAlias = productAlias;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

}