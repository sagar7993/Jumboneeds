package com.jumboneeds.controllers;

import com.jumboneeds.beans.ExecutiveHomeBean;
import com.jumboneeds.beans.HomeBean;
import com.jumboneeds.beans.IdBean;
import com.jumboneeds.beans.PartnerHomeBean;
import com.jumboneeds.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @RequestMapping(value = "/getHome", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody HomeBean getHome(@RequestBody IdBean idBean){
        return homeService.getHome(idBean.getId());
    }

    @RequestMapping(value = "/getPartnerHome", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody PartnerHomeBean getPartnerHome(@RequestBody IdBean idBean){
        return homeService.getPartnerHome(idBean.getId());
    }

    @RequestMapping(value = "/getExecutiveHome", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ExecutiveHomeBean getExecutiveHome(@RequestBody IdBean idBean){
        return homeService.getExecutiveHome(idBean.getId());
    }

}