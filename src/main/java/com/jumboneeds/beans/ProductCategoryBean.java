package com.jumboneeds.beans;

public class ProductCategoryBean {

    private String id;

    private String productCategoryName;

    private String productCategoryImageUrl;

    public ProductCategoryBean(){

    }

    public ProductCategoryBean(String id, String productCategoryName, String productCategoryImageUrl) {
        this.id = id;
        this.productCategoryName = productCategoryName;
        this.productCategoryImageUrl = productCategoryImageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getProductCategoryImageUrl() {
        return productCategoryImageUrl;
    }

    public void setProductCategoryImageUrl(String productCategoryImageUrl) {
        this.productCategoryImageUrl = productCategoryImageUrl;
    }
}