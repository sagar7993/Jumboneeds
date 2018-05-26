package com.jumboneeds.controllers;

import com.jumboneeds.beans.BuildingDateBean;
import com.jumboneeds.beans.TransactionLogMaster;
import com.jumboneeds.services.TransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactionLog")
public class TransactionLogController {

    @Autowired
    private TransactionLogService transactionLogService;

    @RequestMapping(value = "/findByBuildingAndDate", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody TransactionLogMaster findByPartner(@RequestBody BuildingDateBean buildingDateBean){
        return transactionLogService.fetchTransactionLogs(buildingDateBean);
    }
}
