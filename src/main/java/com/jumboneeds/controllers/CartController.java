package com.jumboneeds.controllers;

import com.jumboneeds.beans.CartBean;
import com.jumboneeds.beans.CartProductListBean;
import com.jumboneeds.beans.StatusBean;
import com.jumboneeds.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 30-Dec-16.
 */

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/checkOffer", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody CartBean checkOffer(@RequestBody CartProductListBean cartProductListBean) {
        return cartService.checkOffer(cartProductListBean);
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean checkout(@RequestBody CartProductListBean cartProductListBean) {
        return cartService.checkout(cartProductListBean);
    }
}
