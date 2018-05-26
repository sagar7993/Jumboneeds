package com.jumboneeds.services;

import com.jumboneeds.entities.PaymentType;
import com.jumboneeds.repositories.PaymentTypeRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentTypeService {
    private static final String TAG = "PaymentTypeService : ";

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public PaymentType findByType(int type){
        return paymentTypeRepository.findByType(type);
    }

    public PaymentType findById(String id){
        return paymentTypeRepository.findById(id);
    }

}