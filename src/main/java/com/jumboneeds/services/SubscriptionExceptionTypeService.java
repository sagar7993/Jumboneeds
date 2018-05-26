package com.jumboneeds.services;

import com.jumboneeds.entities.SubscriptionExceptionType;
import com.jumboneeds.repositories.SubscriptionExceptionTypeRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionExceptionTypeService {
    private static final String TAG = "SubscriptionExceptionTypeService : ";

    @Autowired
    private SubscriptionExceptionTypeRepository subscriptionExceptionTypeRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public SubscriptionExceptionType findByType(int type){
        return subscriptionExceptionTypeRepository.findByType(type);
    }

    public SubscriptionExceptionType findById(String id){
        return subscriptionExceptionTypeRepository.findById(id);
    }

}