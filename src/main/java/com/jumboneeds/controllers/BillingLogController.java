package com.jumboneeds.controllers;

import com.jumboneeds.beans.BillingLogHistoryBean;
import com.jumboneeds.beans.IdBean;
import com.jumboneeds.entities.BillingLog;
import com.jumboneeds.services.BillingLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/billingLog")
public class BillingLogController {

    @Autowired
    private BillingLogService billingLogService;

    @RequestMapping(value = "/findByUser", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody BillingLogHistoryBean findByUser(@RequestBody IdBean idBean){
        return billingLogService.findByUser(idBean);
    }

    @RequestMapping(value = "/getUserBillingLogs", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody List<BillingLog> getUserBillingLogs(@RequestBody IdBean idBean){
        return billingLogService.getUserBillingLogs(idBean);
    }

    @RequestMapping(value = "/getAllUserBillingLogsBetweenDates", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody List<BillingLog> getAllUserBillingLogsBetweenDates(@RequestBody IdBean idBean){
        return billingLogService.getAllUserBillingLogsBetweenDates(idBean);
    }

}