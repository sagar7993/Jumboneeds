package com.jumboneeds.services;

import com.jumboneeds.entities.CashPickUpRequest;
import com.jumboneeds.repositories.CashPickUpRequestRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashPickUpRequestService {
    private static final String TAG = "CashPickUpRequestService : ";

    @Autowired
    private CashPickUpRequestRepository cashPickUpRequestRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public void save(CashPickUpRequest cashPickUpRequest){
        cashPickUpRequestRepository.save(cashPickUpRequest);
    }

}