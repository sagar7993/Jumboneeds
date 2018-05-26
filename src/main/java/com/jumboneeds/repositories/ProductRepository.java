package com.jumboneeds.repositories;

import com.jumboneeds.beans.ProductBean;
import com.jumboneeds.beans.ProductSubCategoryBean;
import com.jumboneeds.entities.Building;
import com.jumboneeds.entities.Product;
import com.jumboneeds.entities.ProductMaster;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {

    @Transactional
    Product findById(@Param("id") String id);

    @Transactional
    @Query(value = "SELECT p FROM Product p WHERE p.building IN :buildings")
    List<Product> findByBuildings(@Param("buildings") List<Building> buildings);

    @Transactional
    @Query(value = "SELECT p FROM Product p WHERE p.building = :building AND p.status <> 3 AND p.productMaster.productSubCategory.status = 1 and p.productMaster.status = 1 and p.flashStatus <> 1 order by p.productMaster.weight desc")
    List<Product> findByBuilding(@Param("building") Building building);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.ProductBean(p.id, p.productMaster.productName, p.productAlias, p.productMaster.productUnitSize, p.productUnitPrice, p.building.buildingName, p.productMaster.productSubCategory.productSubCategoryName, p.productMaster.productSubCategory.productCategory.productCategoryName, p.fulfilledByPartner, p.status, p.productMaster.productImageUrl) FROM Product p WHERE p.building.id = :buildingId")
    List<ProductBean> findByBuilding(@Param("buildingId") String buildingId);

    @Transactional
    @Query(value = "SELECT p FROM Product p WHERE p.building = :building AND p.flashStatus = 1")
    List<Product> findFlashProductsByBuilding(@Param("building") Building building);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.ProductBean(p.id, p.productMaster.productName, p.productAlias, p.productMaster.productUnitSize, p.productUnitPrice, p.strikePrice, p.specialText, p.building.buildingName, p.productMaster.productSubCategory.productSubCategoryName, p.productMaster.productSubCategory.productCategory.productCategoryName, p.fulfilledByPartner, p.status, p.productMaster.productImageUrl) FROM Product p WHERE p.building = :building AND p.flashStatus = 1")
    List<ProductBean> findFlashProductBeansByBuilding(@Param("building") Building building);

    @Transactional
    @Query(value = "SELECT p FROM Product p WHERE p.productMaster = :productMaster AND p.flashStatus = 1")
    List<Product> findFlashProductsByProductMaster(@Param("productMaster") ProductMaster productMaster);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.ProductBean(p.id, p.productMaster.productName, p.productAlias, p.productMaster.productUnitSize, p.productUnitPrice, p.strikePrice, p.specialText, p.building.buildingName, p.productMaster.productSubCategory.productSubCategoryName, p.productMaster.productSubCategory.productCategory.productCategoryName, p.fulfilledByPartner, p.status, p.productMaster.productImageUrl) FROM Product p WHERE p.productMaster = :productMaster AND p.flashStatus = 1")
    List<ProductBean> findFlashProductBeansByProductMaster(@Param("productMaster") ProductMaster productMaster);

    @Transactional
    @Query(value = "SELECT DISTINCT new com.jumboneeds.beans.ProductSubCategoryBean(p.productMaster.productSubCategory.id, p.productMaster.productSubCategory.productSubCategoryName, p.productMaster.productSubCategory.productSubCategoryImageUrl, p.productMaster.productSubCategory.productSubCategoryImageUrlSmall) FROM Product p WHERE p.building.id = :buildingId AND p.productMaster.productSubCategory.status = 1")
    List<ProductSubCategoryBean> findProductSubCategoryBeansByBuilding(@Param("buildingId") String buildingId);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.ProductBean(p.id, p.productMaster.productName, p.productMaster.productImageUrl, p.productUnitPrice, p.productMaster.productUnitSize, p.productAlias, p.specialText, p.flashStatus, p.strikePrice) FROM Product p WHERE p.building.id = :buildingId and p.flashStatus = 1")
    ProductBean findFlashSaleProductByBuilding(@Param("buildingId") String buildingId);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.ProductBean(p.id, p.productMaster.productName, p.productAlias, p.productMaster.productUnitSize, p.productUnitPrice, p.building.buildingName, p.productMaster.productSubCategory.productSubCategoryName, p.productMaster.productSubCategory.productCategory.productCategoryName, p.fulfilledByPartner, p.status, p.productMaster.productImageUrl) FROM Product p WHERE p.productMaster.id = :productMasterId")
    List<ProductBean> findByProductMaster(@Param("productMasterId") String productMasterId);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.ProductBean(p.id, p.productMaster.productName, p.productAlias, p.productMaster.productUnitSize, p.productUnitPrice, p.building.buildingName, p.productMaster.productSubCategory.productSubCategoryName, p.productMaster.productSubCategory.productCategory.productCategoryName, p.fulfilledByPartner, p.status, p.productMaster.productImageUrl) FROM Product p WHERE p.productMaster.id = :productMasterId AND p.building.id = :buildingId")
    List<ProductBean> findByProductMasterAndBuilding(@Param("productMasterId") String productMasterId, @Param("buildingId") String buildingId);

    @Transactional
    @Query(value = "SELECT p FROM Product p WHERE p.productMaster = :productMaster AND p.building IN :buildingList")
    List<Product> findByProductMasterAndBuildings(@Param("productMaster") ProductMaster productMaster, @Param("buildingList") List<Building> buildingList);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Product p SET p.productAlias = :productAlias WHERE p.productMaster = :productMaster")
    void updateProductAliasForProductByProductMaster(@Param("productMaster") ProductMaster productMaster, @Param("productAlias") String productAlias);

}