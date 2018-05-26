package com.jumboneeds.beans;

import com.jumboneeds.entities.ProductSubCategory;

import java.util.ArrayList;
import java.util.List;

public class ProductSubCategoryBean {

    private String id;

    private String productSubCategoryName;

    private String productSubCategoryImageUrl;

    private String productSubCategoryImageUrlSmall;

    private List<ProductBean> productBeans = new ArrayList<>();

    public ProductSubCategoryBean(){

    }

    public ProductSubCategoryBean(String id, String productSubCategoryName, String productSubCategoryImageUrl, String productSubCategoryImageUrlSmall) {
        this.id = id;
        this.productSubCategoryName = productSubCategoryName;
        this.productSubCategoryImageUrl = productSubCategoryImageUrl;
        this.productSubCategoryImageUrlSmall = productSubCategoryImageUrlSmall;
    }

    public ProductSubCategoryBean(ProductSubCategory productSubCategory) {
        id = productSubCategory.getId();
        productSubCategoryName = productSubCategory.getProductSubCategoryName();
        productSubCategoryImageUrl = productSubCategory.getProductSubCategoryImageUrl();
        productSubCategoryImageUrlSmall = productSubCategory.getProductSubCategoryImageUrlSmall();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductSubCategoryName() {
        return productSubCategoryName;
    }

    public void setProductSubCategoryName(String productSubCategoryName) {
        this.productSubCategoryName = productSubCategoryName;
    }

    public String getProductSubCategoryImageUrl() {
        return productSubCategoryImageUrl;
    }

    public void setProductSubCategoryImageUrl(String productSubCategoryImageUrl) {
        this.productSubCategoryImageUrl = productSubCategoryImageUrl;
    }

    public String getProductSubCategoryImageUrlSmall() {
        return productSubCategoryImageUrlSmall;
    }

    public void setProductSubCategoryImageUrlSmall(String productSubCategoryImageUrlSmall) {
        this.productSubCategoryImageUrlSmall = productSubCategoryImageUrlSmall;
    }

    public List<ProductBean> getProductBeans() {
        return productBeans;
    }

    public void setProductBeans(List<ProductBean> productBeans) {
        this.productBeans = productBeans;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductSubCategoryBean)) return false;
        ProductSubCategoryBean that = (ProductSubCategoryBean) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

}