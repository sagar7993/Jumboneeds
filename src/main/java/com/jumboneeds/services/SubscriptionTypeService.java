package com.jumboneeds.services;

import com.jumboneeds.entities.SubscriptionType;
import com.jumboneeds.repositories.SubscriptionTypeRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionTypeService {
    private static final String TAG = "SubscriptionTypeService : ";

    @Autowired
    private SubscriptionTypeRepository subscriptionTypeRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public SubscriptionType findByType(int type){
        return subscriptionTypeRepository.findByType(type);
    }

    public SubscriptionType findById(String id){
        return subscriptionTypeRepository.findById(id);
    }

    public List<SubscriptionType> getSubscriptionTypes(){
        return (List<SubscriptionType>) subscriptionTypeRepository.findAll();
    }

}