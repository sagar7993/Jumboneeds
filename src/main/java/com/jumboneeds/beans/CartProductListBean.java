package com.jumboneeds.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 30-Dec-16.
 */
public class CartProductListBean {

    private List<CartProductBean> cartProducts = new ArrayList<>();

    public CartProductListBean() {

    }

    public List<CartProductBean> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProductBean> cartProducts) {
        this.cartProducts = cartProducts;
    }
}

