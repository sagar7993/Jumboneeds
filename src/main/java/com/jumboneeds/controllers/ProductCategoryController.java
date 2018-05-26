package com.jumboneeds.controllers;

import com.jumboneeds.beans.ProductCategoryBean;
import com.jumboneeds.entities.ProductCategory;
import com.jumboneeds.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public void save(@RequestBody ProductCategory productCategory){
        productCategoryService.save(productCategory);
    }

    @RequestMapping(value = "/fetchAll", method = RequestMethod.GET)
    public @ResponseBody List<ProductCategoryBean> fetchAll(){
        return productCategoryService.fetchAll();
    }

}