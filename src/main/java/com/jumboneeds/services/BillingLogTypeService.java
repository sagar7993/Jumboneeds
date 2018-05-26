package com.jumboneeds.services;

import com.jumboneeds.entities.BillingLogType;
import com.jumboneeds.repositories.BillingLogTypeRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillingLogTypeService {
    private static final String TAG = "BillingLogTypeService : ";

    @Autowired
    private BillingLogTypeRepository billingLogTypeRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public BillingLogType findByType(int type){
        return billingLogTypeRepository.findByType(type);
    }

    public BillingLogType findById(String id){
        return billingLogTypeRepository.findById(id);
    }

}