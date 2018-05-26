package com.jumboneeds.services;

import com.jumboneeds.beans.ReasonTypesBean;
import com.jumboneeds.entities.ReasonType;
import com.jumboneeds.repositories.ReasonTypeRepository;
import com.jumboneeds.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReasonTypeService {
    private static final String TAG = "ReasonTypeService : ";

    @Autowired
    private ReasonTypeRepository reasonTypeRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public ReasonType findByType(int type){
        return reasonTypeRepository.findByType(type);
    }

    public ReasonType findById(String id){
        return reasonTypeRepository.findById(id);
    }

    public ReasonTypesBean fetchAllReasonTypeBeans(){
        ReasonTypesBean reasonTypesBean = new ReasonTypesBean();

        try {
            reasonTypesBean.setReasonTypeBeans(reasonTypeRepository.fetchAllReasonTypeBeans());

            reasonTypesBean.setStatus(1);
        } catch (Exception e){
            debugLogger.debug(TAG + "Error in fetching all ReasonTypeBeans");

            reasonTypesBean.setStatus(1);
            reasonTypesBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        }

        return reasonTypesBean;
    }

}