package com.jumboneeds.services;

import com.jumboneeds.utils.Constants;
import com.jumboneeds.utils.DateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by akash.mercer on 18-Oct-16.
 */

@Service
public class DatabaseBackupService {
    private static final String TAG = "DatabaseBackupService : ";

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    @Scheduled(cron = "0 0 0/6 * * *")
    public void backup(){
        try {
            Runtime runtime = Runtime.getRuntime();

            Process process = runtime.exec("mysqldump -u root -pMilano --add-drop-table=0 -B jumboneeds -r " + Constants.DATABASE_DUMP_PATH + DateOperations.getDateStringForDump(new Date()) + ".sql");

            int processComplete = process.waitFor();

            if (processComplete == 0) {
                debugLogger.debug(TAG + "Backup created successfully!");
            } else {
                debugLogger.debug(TAG + "Could not create the backup");
            }
        } catch (Exception e) {
            errorLogger.error(TAG + "Database backup service failed");
        }
    }
}
