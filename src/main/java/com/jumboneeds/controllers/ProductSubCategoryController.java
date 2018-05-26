package com.jumboneeds.controllers;

import com.jumboneeds.beans.ProductSubCategoryBean;
import com.jumboneeds.entities.ProductSubCategory;
import com.jumboneeds.services.ProductSubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/productSubCategory")
public class ProductSubCategoryController {

    @Autowired
    private ProductSubCategoryService productSubCategoryService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public void save(@RequestBody ProductSubCategory productSubCategory){
        productSubCategoryService.save(productSubCategory);
    }

    @RequestMapping(value = "/fetchAll", method = RequestMethod.GET)
    public @ResponseBody List<ProductSubCategoryBean> fetchAll(){
        return productSubCategoryService.fetchAllProductSubCategoryBeans();
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public @ResponseBody List<ProductSubCategoryBean> findAll(){
        return productSubCategoryService.fetchAll();
    }

}