package com.jumboneeds.controllers;

import com.jumboneeds.beans.LoginResultBean;
import com.jumboneeds.beans.OtpLoginBean;
import com.jumboneeds.services.ExecutiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/executive")
public class ExecutiveController {

    @Autowired
    private ExecutiveService executiveService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody LoginResultBean login(@RequestBody OtpLoginBean otpLoginBean){
        return executiveService.login(otpLoginBean);
    }

}