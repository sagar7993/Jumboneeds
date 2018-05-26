package com.jumboneeds.controllers;

import com.jumboneeds.beans.InventoryMaster;
import com.jumboneeds.beans.NoAuthLogin;
import com.jumboneeds.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 26-Nov-16.
 */

@RestController
@RequestMapping(value = "/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @RequestMapping(value = "/getInventory", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody InventoryMaster getInventory(@RequestBody NoAuthLogin noAuthLogin) {
        return inventoryService.fetchInventory(noAuthLogin);
    }
}
