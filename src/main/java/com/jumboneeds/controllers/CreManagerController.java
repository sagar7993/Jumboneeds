package com.jumboneeds.controllers;

import com.jumboneeds.beans.CreManagerExecutiveBean;
import com.jumboneeds.beans.StatusBean;
import com.jumboneeds.services.CreManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 19-Oct-16.
 */

@RestController
@RequestMapping(value = "/creManager")
public class CreManagerController {

    @Autowired
    private CreManagerService creManagerService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean save(@RequestBody CreManagerExecutiveBean creManagerExecutiveBean){
        return creManagerService.save(creManagerExecutiveBean);
    }
}
