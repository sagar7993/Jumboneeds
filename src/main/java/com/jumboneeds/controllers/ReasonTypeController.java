package com.jumboneeds.controllers;

import com.jumboneeds.beans.ReasonTypesBean;
import com.jumboneeds.services.ReasonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by akash.mercer on 19-Oct-16.
 */

@RestController
@RequestMapping(value = "/reasonType")
public class ReasonTypeController {

    @Autowired
    private ReasonTypeService reasonTypeService;

    @RequestMapping(value = "/fetchAllReasonTypeBeans", method = RequestMethod.GET)
    public @ResponseBody ReasonTypesBean fetchAllReasonTypeBeans(){
        return reasonTypeService.fetchAllReasonTypeBeans();
    }
}
