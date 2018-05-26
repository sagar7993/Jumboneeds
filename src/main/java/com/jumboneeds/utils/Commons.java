package com.jumboneeds.utils;

import com.jumboneeds.beans.CartBean;
import com.jumboneeds.beans.CartProductBean;
import com.jumboneeds.beans.ProductBean;

/**
 * Created by akash.mercer on 30-Dec-16.
 */
public class Commons {

    public static Double getCartTotal(CartBean cartBean) {
        Double cartTotal = 0.0;

        for (ProductBean productBean : cartBean.getProducts()) {
            cartTotal = productBean.getProductUnitPrice() * productBean.getProductQuantity();
        }

        return cartTotal;
    }

    public static Integer getTotalProductsCount(CartBean cartBean) {
        Integer totalProductsCount = 0;

        for (CartProductBean cartProductBean : cartBean.getCartProducts()) {
            totalProductsCount = cartProductBean.getProductQuantity();
        }

        return totalProductsCount;
    }

    public static void clear(CartBean cartBean) {
        cartBean.getProducts().clear();
        cartBean.getCartProducts().clear();
    }

}
