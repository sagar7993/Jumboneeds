package com.jumboneeds.repositories;

import com.jumboneeds.beans.BuildingBean;
import com.jumboneeds.entities.Building;
import com.jumboneeds.entities.ProductMaster;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BuildingRepository extends CrudRepository<Building, String> {

    @Transactional
    Building findById(@Param("id") String id);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.BuildingBean(b.id, b.buildingName, b.address) FROM Building b WHERE b.status = 1 ORDER BY b.buildingName")
    List<BuildingBean> fetchAllActiveBuildingBeans();

    @Transactional
    @Query(value = "SELECT b FROM Building b WHERE b.status = 1 ORDER BY b.buildingName")
    List<Building> fetchAll();

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.BuildingBean(b.id, b.buildingName, b.address) FROM Building b WHERE b.id = :id")
    BuildingBean findBuildingBeanById(@Param("id") String id);

    @Transactional
    @Query(value = "SELECT b FROM Building b WHERE b.partner.id = :partnerId")
    Building findByPartner(@Param("partnerId") String partnerId);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.BuildingBean(b.id, b.buildingName, b.address) FROM Building b WHERE b.parentPartner.id = :parentPartnerId")
    List<BuildingBean> findBuildingBeansByParentPartner(@Param("parentPartnerId") String parentPartnerId);

    @Transactional
    @Query(value = "SELECT b FROM Building b WHERE b.parentPartner.id = :partnerId AND b.status = 1 ORDER BY b.buildingName ASC")
    List<Building> findByParentPartner(@Param("partnerId") String partnerId);

    @Transactional
    @Query(value = "SELECT b FROM Building b WHERE b.id IN :buildingIdList")
    List<Building> fetchBuildingsByIdList(@Param("buildingIdList") List<String> buildingIdList);

    @Transactional
    @Query(value = "SELECT b FROM Building b WHERE b.parentPartner.id IN :parentPartnerIdList")
    List<Building> fetchBuildingsByParentPartnerIdList(@Param("parentPartnerIdList") List<String> parentPartnerIdList);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.BuildingBean(b.id, b.buildingName, b.address) FROM Building b WHERE b.id NOT IN (SELECT p.building.id FROM Product p WHERE p.productMaster = :productMaster)")
    List<BuildingBean> buildingListNotHavingProductMaster(@Param("productMaster") ProductMaster productMaster);

}