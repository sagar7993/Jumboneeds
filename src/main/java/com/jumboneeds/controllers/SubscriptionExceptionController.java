package com.jumboneeds.controllers;

import com.jumboneeds.beans.AdminExceptionBean;
import com.jumboneeds.beans.StatusBean;
import com.jumboneeds.beans.SubscriptionExceptionBean;
import com.jumboneeds.beans.SubscriptionExceptionUpdateBean;
import com.jumboneeds.services.SubscriptionExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/subscriptionException")
public class SubscriptionExceptionController {

    @Autowired
    private SubscriptionExceptionService subscriptionExceptionService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody SubscriptionExceptionUpdateBean save(@RequestBody SubscriptionExceptionBean subscriptionExceptionBean){
        return subscriptionExceptionService.saveSubscriptionException(subscriptionExceptionBean);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean update(@RequestBody SubscriptionExceptionBean subscriptionExceptionBean){
        return subscriptionExceptionService.updateSubscriptionException(subscriptionExceptionBean);
    }

    @RequestMapping(value = "/createAdminException", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean createAdminException(@RequestBody AdminExceptionBean adminExceptionBean){
        return subscriptionExceptionService.createAdminException(adminExceptionBean);
    }

}