package com.jumboneeds.repositories;

import com.jumboneeds.beans.ProductBean;
import com.jumboneeds.beans.ProductMasterBean;
import com.jumboneeds.entities.Building;
import com.jumboneeds.entities.ProductMaster;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductMasterRepository extends CrudRepository<ProductMaster, String> {

    @Transactional
    ProductMaster findById(@Param("id") String id);

    @Transactional
    @Query(value = "SELECT p FROM ProductMaster p WHERE p.productName = :productName AND p.productUnitSize = :productUnitSize")
    List<ProductMaster> checkUniqueProductMaster(@Param("productName") String productName, @Param("productUnitSize") String productUnitSize);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.ProductBean(prod.id, prod.productName, prod.productAlias, prod.productUnitSize) FROM ProductMaster prod WHERE prod.id NOT IN (SELECT p.productMaster.id FROM Product p WHERE p.building IN :buildingList)")
    List<ProductBean> productMasterListNotAddedInBuilding(@Param("buildingList") List<Building> buildingList);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.ProductBean(prod.id, prod.productName, prod.productAlias, prod.productUnitSize) FROM ProductMaster prod WHERE prod.id NOT IN (SELECT p.productMaster.id FROM Product p WHERE p.building = :building)")
    List<ProductBean> productMasterListNotAddedInBuilding(@Param("building") Building building);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.ProductMasterBean(p.id, p.productName, p.productImageUrl, p.productUnitSize, p.productAlias) FROM ProductMaster p")
    List<ProductMasterBean> fetchAll();

    @Transactional
    @Modifying
    @Query(value = "update ProductMaster p set p.status = :status where p.id = :id")
    void updateProductMasterStatus(@Param("status") Integer status, @Param("id") String id);

}