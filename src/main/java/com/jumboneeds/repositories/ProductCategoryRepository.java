package com.jumboneeds.repositories;

import com.jumboneeds.beans.ProductCategoryBean;
import com.jumboneeds.entities.ProductCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, String> {

    @Transactional
    ProductCategory findById(@Param("id") String id);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.ProductCategoryBean(p.id, p.productCategoryName, p.productCategoryImageUrl) FROM ProductCategory p WHERE p.status = 1")
    List<ProductCategoryBean> fetchAllProductCategoryBeans();

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.ProductCategoryBean(p.id, p.productCategoryName, p.productCategoryImageUrl) FROM ProductCategory p")
    List<ProductCategoryBean> fetchAll();

}