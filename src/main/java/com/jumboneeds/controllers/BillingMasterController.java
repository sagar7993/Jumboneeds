package com.jumboneeds.controllers;

import com.jumboneeds.beans.IdBean;
import com.jumboneeds.beans.TransactionHistoryBean;
import com.jumboneeds.beans.WalletBalanceBean;
import com.jumboneeds.services.BillingMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billingMaster")
public class BillingMasterController {

    @Autowired
    private BillingMasterService billingMasterService;

    @RequestMapping(value = "/findByUser", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody WalletBalanceBean findByUser(@RequestBody IdBean idBean){
        return billingMasterService.findByUser(idBean);
    }

    @RequestMapping(value = "/getTransactionHistory", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody TransactionHistoryBean getTransactionHistory(@RequestBody IdBean idBean){
        return billingMasterService.getTransactionHistory(idBean);
    }

}