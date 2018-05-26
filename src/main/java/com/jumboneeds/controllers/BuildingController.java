package com.jumboneeds.controllers;

import com.jumboneeds.beans.*;
import com.jumboneeds.entities.Building;
import com.jumboneeds.entities.ProductMaster;
import com.jumboneeds.services.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/building")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public void save(@RequestBody Building building){
        buildingService.save(building);
    }

    @RequestMapping(value = "/fetchAllActiveBuildings", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody BuildingsBean fetchAllActiveBuildings(@RequestBody IdBean idBean) {
        return buildingService.fetchBuildingBeansByUser(idBean);
    }

    @RequestMapping(value = "/getAllActiveBuildings", method = RequestMethod.GET)
    public @ResponseBody BuildingsBean getAllActiveBuildings(){ return buildingService.getAllActiveBuildings(); }

    @RequestMapping(value = "/getActiveBuildings", method = RequestMethod.GET)
    public @ResponseBody List<BuildingBean> getActiveBuildings(){ return buildingService.getActiveBuildings(); }

    @RequestMapping(value = "/findById", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody BuildingBean findById(@RequestBody Building building){
        return buildingService.findBuildingBeanById(building.getId());
    }

    @RequestMapping(value = "/findBuildingById", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody Building findBuildingById(@RequestBody Building building){
        return buildingService.findById(building);
    }

    @RequestMapping(value = "/findAllBuildings", method = RequestMethod.GET)
    public @ResponseBody List<AddBuildingBean> findAllBuildings(){
        return buildingService.findAllBuildings(null, false);
    }

    @RequestMapping(value = "/findBuildingBeansByParentPartner", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody BuildingsBean findBuildingBeansByParentPartner(@RequestBody IdBean idBean){
        return buildingService.findBuildingBeansByParentPartner(idBean);
    }

    @RequestMapping(value = "/addBuilding", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean addBuilding(@RequestBody AddBuildingBean addBuildingBean){
        return buildingService.addBuilding(addBuildingBean);
    }

    @RequestMapping(value = "/editBuilding", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean editBuilding(@RequestBody AddBuildingBean addBuildingBean){
        return buildingService.editBuilding(addBuildingBean);
    }

    @RequestMapping(value = "/buildingListNotHavingProductMaster", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody List<BuildingBean> buildingListNotHavingProductMaster(@RequestBody ProductMaster productMaster){
        return buildingService.buildingListNotHavingProductMaster(productMaster.getId());
    }

}