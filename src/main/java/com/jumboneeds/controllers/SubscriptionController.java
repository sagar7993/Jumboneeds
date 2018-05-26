package com.jumboneeds.controllers;

import com.jumboneeds.beans.*;
import com.jumboneeds.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean save(@RequestBody SubscriptionBean subscriptionBean){
        return subscriptionService.saveSubscription(subscriptionBean);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean update(@RequestBody SubscriptionBean subscriptionBean){
        return subscriptionService.updateSubscription(subscriptionBean);
    }

    @RequestMapping(value = "/end", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean end(@RequestBody IdBean idBean){
        return subscriptionService.endSubscription(idBean);
    }

    @RequestMapping(value = "/saveFlashSaleSubscription", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody FlashSaleResponseBean end(@RequestBody SubscriptionBean subscriptionBean){
        return subscriptionService.saveFlashSaleSubscription(subscriptionBean);
    }

    @RequestMapping(value = "/createAdminSubscription", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean createAdminSubscription(@RequestBody AdminSubscriptionBean adminSubscriptionBean){
        return subscriptionService.createAdminSubscription(adminSubscriptionBean);
    }

    @RequestMapping(value = "/updateAdminSubscription", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean updateAdminSubscription(@RequestBody AdminSubscriptionBean adminSubscriptionBean){
        return subscriptionService.updateAdminSubscription(adminSubscriptionBean);
    }

}