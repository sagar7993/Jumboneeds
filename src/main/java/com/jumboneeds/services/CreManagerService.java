package com.jumboneeds.services;

import com.jumboneeds.beans.CreManagerExecutiveBean;
import com.jumboneeds.beans.StatusBean;
import com.jumboneeds.entities.Building;
import com.jumboneeds.entities.CreManager;
import com.jumboneeds.entities.Executive;
import com.jumboneeds.entities.ReasonType;
import com.jumboneeds.repositories.CreManagerRepository;
import com.jumboneeds.utils.Constants;
import com.jumboneeds.utils.DateOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreManagerService {
    private static final String TAG = "CreManagerService : ";

    @Autowired
    private CreManagerRepository creManagerRepository;

    @Autowired
    private ExecutiveService executiveService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private ReasonTypeService reasonTypeService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public StatusBean save(CreManagerExecutiveBean creManagerExecutiveBean){
        StatusBean statusBean = new StatusBean();

        Executive retrievedExecutive = executiveService.findById(creManagerExecutiveBean.getExecutiveId());

        if (retrievedExecutive == null){
            debugLogger.debug(TAG + "No Executive found for Id :" + creManagerExecutiveBean.getExecutiveId());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        Building retrievedBuilding = buildingService.findById(creManagerExecutiveBean.getBuildingId());

        if (retrievedBuilding == null){
            debugLogger.debug(TAG + "No Building found for Id :" + creManagerExecutiveBean.getBuildingId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        ReasonType retrievedReasonType = reasonTypeService.findById(creManagerExecutiveBean.getReasonId());

        if (retrievedReasonType == null){
            debugLogger.debug(TAG + "No Reason found for Id :" + creManagerExecutiveBean.getReasonId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        try {
            CreManager creManager = new CreManager();
            creManager.setActive(true);
            creManager.setBuilding(retrievedBuilding);
            creManager.setDate(DateOperations.getTodayStartDate());
            creManager.setFlat(creManagerExecutiveBean.getFlat());
            creManager.setExecutive(retrievedExecutive);
            creManager.setReasonType(retrievedReasonType);

            creManagerRepository.save(creManager);

            statusBean.setStatus(1);
        } catch (Exception e){
            debugLogger.debug(TAG + "");

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        }

        return statusBean;
    }
}