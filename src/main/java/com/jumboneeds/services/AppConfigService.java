package com.jumboneeds.services;

import com.jumboneeds.entities.AppConfig;
import com.jumboneeds.repositories.AppConfigRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class AppConfigService {
    private static final String TAG = "AppConfigService : ";

    @Autowired
    private AppConfigRepository appConfigRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public void save(AppConfig appConfig){
        appConfigRepository.save(appConfig);
    }

    public AppConfig getAppConfig(){
        List<AppConfig> appConfigs = (List<AppConfig>) appConfigRepository.findAll();

        if(CollectionUtils.isEmpty(appConfigs)){
            debugLogger.debug(TAG + "No AppConfig object found");

            return null;
        }

        return appConfigs.get(0);
    }

}