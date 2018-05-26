package com.jumboneeds.beans;

import java.util.List;

public class FlashSaleProductBean {

    private List<ProductBean> buildingFlashSaleList;

    private List<ProductBean> productFlashSaleList;

    public FlashSaleProductBean(){

    }

    public FlashSaleProductBean(List<ProductBean> buildingFlashSaleList, List<ProductBean> productFlashSaleList) {
        this.buildingFlashSaleList = buildingFlashSaleList;
        this.productFlashSaleList = productFlashSaleList;
    }

    public List<ProductBean> getBuildingFlashSaleList() {
        return buildingFlashSaleList;
    }

    public void setBuildingFlashSaleList(List<ProductBean> buildingFlashSaleList) {
        this.buildingFlashSaleList = buildingFlashSaleList;
    }

    public List<ProductBean> getProductFlashSaleList() {
        return productFlashSaleList;
    }

    public void setProductFlashSaleList(List<ProductBean> productFlashSaleList) {
        this.productFlashSaleList = productFlashSaleList;
    }

}