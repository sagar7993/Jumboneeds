package com.jumboneeds.beans;

import java.util.ArrayList;
import java.util.List;

public class CartBean {

    private List<ProductBean> products = new ArrayList<>();

    private List<CartProductBean> cartProducts = new ArrayList<>();

    public CartBean(){

    }

    public List<ProductBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductBean> products) {
        this.products = products;
    }

    public List<CartProductBean> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProductBean> cartProducts) {
        this.cartProducts = cartProducts;
    }

}