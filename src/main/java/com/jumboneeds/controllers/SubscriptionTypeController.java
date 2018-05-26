package com.jumboneeds.controllers;

import com.jumboneeds.entities.SubscriptionType;
import com.jumboneeds.services.SubscriptionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/subscriptionType")
public class SubscriptionTypeController {

    @Autowired
    private SubscriptionTypeService subscriptionTypeService;

    @RequestMapping(value = "/getSubscriptionTypes", method = RequestMethod.GET)
    public @ResponseBody List<SubscriptionType> getSubscriptionTypes(){
        return subscriptionTypeService.getSubscriptionTypes();
    }

}