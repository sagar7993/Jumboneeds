package com.jumboneeds.beans;

import java.util.ArrayList;
import java.util.List;

public class ProductSubCategoriesBean extends StatusBean{

    private List<ProductSubCategoryBean> productSubCategoryBeans = new ArrayList<>();

    public ProductSubCategoriesBean(){

    }

    public List<ProductSubCategoryBean> getProductSubCategoryBeans() {
        return productSubCategoryBeans;
    }

    public void setProductSubCategoryBeans(List<ProductSubCategoryBean> productSubCategoryBeans) {
        this.productSubCategoryBeans = productSubCategoryBeans;
    }

}