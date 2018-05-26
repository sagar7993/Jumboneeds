package com.jumboneeds.controllers;

import com.jumboneeds.services.StorePrintingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by akash.mercer on 19-Oct-16.
 */

@RestController
@RequestMapping(value = "/storePrint")
public class StorePrintingController {

    @Autowired
    private StorePrintingService storePrintingService;

    @RequestMapping(method = RequestMethod.GET)
    private void runStorePrints(){
        storePrintingService.runStorePrints();
    }
}
