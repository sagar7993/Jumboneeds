package com.jumboneeds.controllers;

import com.jumboneeds.beans.LoginResultBean;
import com.jumboneeds.beans.OtpLoginBean;
import com.jumboneeds.beans.ParentPartnersBean;
import com.jumboneeds.beans.PartnerDashboardBean;
import com.jumboneeds.entities.Partner;
import com.jumboneeds.services.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/partner")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public void save(@RequestBody Partner partner){
        partnerService.save(partner);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody LoginResultBean login(@RequestBody OtpLoginBean otpLoginBean){
        return partnerService.login(otpLoginBean);
    }

    @RequestMapping(value = "/getParentPartners", method = RequestMethod.GET)
    public @ResponseBody List<ParentPartnersBean> getParentPartners(){
        return partnerService.getParentPartners();
    }

}