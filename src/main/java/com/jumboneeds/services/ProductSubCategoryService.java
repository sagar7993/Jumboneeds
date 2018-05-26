package com.jumboneeds.services;

import com.jumboneeds.beans.ProductSubCategoryBean;
import com.jumboneeds.entities.ProductSubCategory;
import com.jumboneeds.repositories.ProductSubCategoryRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSubCategoryService {
    private static final String TAG = "ProductSubCategoryService : ";

    @Autowired
    private ProductSubCategoryRepository productSubCategoryRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public void save(ProductSubCategory productSubCategory){
        productSubCategoryRepository.save(productSubCategory);
    }

    public ProductSubCategory findById(String id){
        return productSubCategoryRepository.findById(id);
    }

    public List<ProductSubCategoryBean> fetchAllProductSubCategoryBeans(){
        return productSubCategoryRepository.fetchAllProductSubCategoryBeans();
    }

    public List<ProductSubCategoryBean> fetchAll(){
        return productSubCategoryRepository.fetchAll();
    }

    public void updateProductSubCategoryStatus(Integer status, String id){
        productSubCategoryRepository.updateProductSubCategoryStatus(status, id);
    }
}