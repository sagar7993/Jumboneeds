package com.jumboneeds.beans;

import java.util.ArrayList;
import java.util.List;

public class HomeBean extends StatusBean{

    private UserBean userBean;

    private List<ProductSubCategoryBean> productSubCategories = new ArrayList<>();

    private List<BannerBean> banners = new ArrayList<>();

    private List<SubscriptionBean> subscriptions = new ArrayList<>();

    private List<SubscriptionBean> comingTomorrowSubscriptions = new ArrayList<>();

    private ProductBean flashSaleProduct;

    private AppConfigBean appConfigBean;

    private Double walletBalance;

    private ProductBean featuredProduct;

    private String contactNumber;

    private String notificationToken;

    public HomeBean(){

    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public List<ProductSubCategoryBean> getProductSubCategories() {
        return productSubCategories;
    }

    public void setProductSubCategories(List<ProductSubCategoryBean> productSubCategories) {
        this.productSubCategories = productSubCategories;
    }

    public List<BannerBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerBean> banners) {
        this.banners = banners;
    }

    public List<SubscriptionBean> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<SubscriptionBean> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<SubscriptionBean> getComingTomorrowSubscriptions() {
        return comingTomorrowSubscriptions;
    }

    public void setComingTomorrowSubscriptions(List<SubscriptionBean> comingTomorrowSubscriptions) {
        this.comingTomorrowSubscriptions = comingTomorrowSubscriptions;
    }

    public ProductBean getFlashSaleProduct() {
        return flashSaleProduct;
    }

    public void setFlashSaleProduct(ProductBean flashSaleProduct) {
        this.flashSaleProduct = flashSaleProduct;
    }

    public AppConfigBean getAppConfigBean() {
        return appConfigBean;
    }

    public void setAppConfigBean(AppConfigBean appConfigBean) {
        this.appConfigBean = appConfigBean;
    }

    public Double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public ProductBean getFeaturedProduct() {
        return featuredProduct;
    }

    public void setFeaturedProduct(ProductBean featuredProduct) {
        this.featuredProduct = featuredProduct;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

}