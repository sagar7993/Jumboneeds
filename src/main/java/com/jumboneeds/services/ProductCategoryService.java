package com.jumboneeds.services;

import com.jumboneeds.beans.ProductCategoryBean;
import com.jumboneeds.entities.ProductCategory;
import com.jumboneeds.repositories.ProductCategoryRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {
    private static final String TAG = "ProductCategoryService : ";

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public void save(ProductCategory productCategory){
        productCategoryRepository.save(productCategory);
    }

    public ProductCategory findById(String id){
        return productCategoryRepository.findById(id);
    }

    public List<ProductCategoryBean> fetchAll(){
        return productCategoryRepository.fetchAll();
    }

}