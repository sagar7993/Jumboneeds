package com.jumboneeds.controllers;

import com.jumboneeds.beans.NoAuthLogin;
import com.jumboneeds.services.BillRegulatorJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/billRegulatorJob")
public class BillRegulatorJobController {

    @Autowired
    private BillRegulatorJobService billRegulatorJobService;

    @RequestMapping(value = "/run", method = RequestMethod.GET)
    public void run(){
        billRegulatorJobService.runBillRegulatorJob();
    }

    @RequestMapping(value = "/runByDate", method = RequestMethod.POST, consumes = "application/json")
    public void runByDate(@RequestBody NoAuthLogin noAuthLogin){
        billRegulatorJobService.runBillRegulatorJob(noAuthLogin.getDate());
    }

}