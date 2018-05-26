package com.jumboneeds.controllers;

import com.jumboneeds.beans.AddProductBean;
import com.jumboneeds.beans.ProductBean;
import com.jumboneeds.beans.StatusBean;
import com.jumboneeds.entities.Building;
import com.jumboneeds.entities.ProductMaster;
import com.jumboneeds.services.ProductMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/productMaster")
public class ProductMasterController {

    @Autowired
    private ProductMasterService productMasterService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public void save(@RequestBody ProductMaster productMaster){
        productMasterService.save(productMaster);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<AddProductBean> findAll(){
        return productMasterService.findAll();
    }

    @RequestMapping(value = "/findById", method = RequestMethod.POST, consumes = "application/json")
    public ProductMaster findById(@RequestBody ProductMaster productMaster){
        return productMasterService.findById(productMaster.getId());
    }

    @RequestMapping(value = "/addProductMaster", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean addProductMaster(@RequestBody AddProductBean addProductBean){
        return productMasterService.addProductMaster(addProductBean);
    }

    @RequestMapping(value = "/editProductMaster", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean editProductMaster(@RequestBody AddProductBean addProductBean){
        return productMasterService.editProductMaster(addProductBean);
    }

    @RequestMapping(value = "/productMasterListNotAddedInBuilding", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody List<ProductBean> productMasterListNotAddedInBuilding(@RequestBody Building building){
        return productMasterService.productMasterListNotAddedInBuilding(building.getId());
    }

}