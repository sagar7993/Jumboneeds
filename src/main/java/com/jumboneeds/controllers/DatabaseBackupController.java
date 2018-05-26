package com.jumboneeds.controllers;

import com.jumboneeds.services.DatabaseBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by akash.mercer on 22-Oct-16.
 */

@RestController
@RequestMapping(value = "/database")
public class DatabaseBackupController {

    @Autowired
    private DatabaseBackupService databaseBackupService;

    @RequestMapping(value = "/backup", method = RequestMethod.GET)
    public void createDump(){
        databaseBackupService.backup();
    }
}
