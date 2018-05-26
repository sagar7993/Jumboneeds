package com.jumboneeds.repositories;

import com.jumboneeds.beans.ProductSubCategoryBean;
import com.jumboneeds.entities.ProductSubCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductSubCategoryRepository extends CrudRepository<ProductSubCategory, String> {

    @Transactional
    ProductSubCategory findById(@Param("id") String id);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.ProductSubCategoryBean(p.id, p.productSubCategoryName, p.productSubCategoryImageUrl, p.productSubCategoryImageUrlSmall) FROM ProductSubCategory p WHERE p.status = 1")
    List<ProductSubCategoryBean> fetchAllProductSubCategoryBeans();

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.ProductSubCategoryBean(p.id, p.productSubCategoryName, p.productSubCategoryImageUrl, p.productSubCategoryImageUrlSmall) FROM ProductSubCategory p")
    List<ProductSubCategoryBean> fetchAll();

    @Transactional
    @Modifying
    @Query(value = "update ProductSubCategory p set p.status = :status where p.id = :id")
    void updateProductSubCategoryStatus(@Param("status") Integer status, @Param("id") String id);
}