package com.jumboneeds.beans;

import com.jumboneeds.entities.Subscription;
import com.jumboneeds.utils.DateOperations;

import java.util.Date;

public class SubscriptionBean {

    private String id;

    private Long startDate;

    private Long endDate;

    private Integer productQuantity;

    private Integer subscriptionType;

    private String subscriptionTypeName;

    private String productId;

    private String productName;

    private String productImageUrl;

    private Double productUnitPrice;

    private String productUnitSize;

    private Boolean flashSaleProduct;

    private String userId;

    public SubscriptionBean() {

    }

    public SubscriptionBean(String id, Date startDate, Date endDate, Integer productQuantity, Integer subscriptionType, String subscriptionTypeName, String productId, String productName, String productImageUrl, Double productUnitPrice, String productUnitSize) {
        this.id = id;
        this.startDate = DateOperations.getServerToClientCustomStartDate(startDate, 0).getTime();
        this.endDate = DateOperations.getServerToClientCustomEndDate(endDate, 0).getTime();
        this.productQuantity = productQuantity;
        this.subscriptionType = subscriptionType;
        this.subscriptionTypeName = subscriptionTypeName;
        this.productId = productId;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.productUnitPrice = productUnitPrice;
        this.productUnitSize = productUnitSize;
    }

    public SubscriptionBean(FilteredSubscriptionBean filteredSubscriptionBean) {
        id = filteredSubscriptionBean.getId();
        startDate = DateOperations.getServerToClientCustomStartDate(filteredSubscriptionBean.getStartDate(), 0).getTime();
        endDate = DateOperations.getServerToClientCustomEndDate(filteredSubscriptionBean.getEndDate(), 0).getTime();
        productQuantity = filteredSubscriptionBean.getProductQuantity();
        subscriptionType = filteredSubscriptionBean.getSubscriptionType();
        productId = filteredSubscriptionBean.getProductId();
        productName = filteredSubscriptionBean.getProductName();
        productImageUrl = filteredSubscriptionBean.getProductImageUrl();
        productUnitPrice = filteredSubscriptionBean.getProductUnitPrice();
        productUnitSize = filteredSubscriptionBean.getProductUnitSize();
    }

    public SubscriptionBean(Subscription subscription) {
        id = subscription.getId();
        startDate = DateOperations.getServerToClientCustomStartDate(subscription.getStartDate(), 0).getTime();
        endDate = DateOperations.getServerToClientCustomEndDate(subscription.getEndDate(), 0).getTime();
        productQuantity = subscription.getProductQuantity();
        subscriptionType = subscription.getSubscriptionType().getType();
        productId = subscription.getProduct().getId();
        productName = subscription.getProduct().getProductMaster().getProductName();
        productImageUrl = subscription.getProduct().getProductMaster().getProductImageUrl();
        productUnitPrice = subscription.getProductUnitPrice();
        productUnitSize = subscription.getProduct().getProductMaster().getProductUnitSize();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Integer getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(Integer subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getSubscriptionTypeName() {
        return subscriptionTypeName;
    }

    public void setSubscriptionTypeName(String subscriptionTypeName) {
        this.subscriptionTypeName = subscriptionTypeName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public Boolean getFlashSaleProduct() {
        return flashSaleProduct;
    }

    public void setFlashSaleProduct(Boolean flashSaleProduct) {
        this.flashSaleProduct = flashSaleProduct;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}