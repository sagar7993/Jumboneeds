package com.jumboneeds.controllers;

import com.jumboneeds.beans.NoAuthLogin;
import com.jumboneeds.services.BillingJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/billingJob")
public class BillingJobController {

    @Autowired
    private BillingJobService billingJobService;

    @RequestMapping(value = "/runByDate", method = RequestMethod.POST, consumes = "application/json")
    public void runByDate(@RequestBody NoAuthLogin noAuthLogin){
        billingJobService.runBillingJob(noAuthLogin.getDate());
    }

}