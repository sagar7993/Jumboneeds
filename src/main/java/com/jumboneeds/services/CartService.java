package com.jumboneeds.services;

import com.jumboneeds.beans.CartBean;
import com.jumboneeds.beans.CartProductListBean;
import com.jumboneeds.beans.StatusBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 30-Dec-16.
 */

@Service
public class CartService {

    @Autowired
    private ProductService productService;

    @Autowired
    private SubscriptionService subscriptionService;

    public CartBean checkOffer(CartProductListBean cartProductListBean) {
        return null;
    }

    public StatusBean checkout(CartProductListBean cartProductListBean) {
        return null;
    }
}
