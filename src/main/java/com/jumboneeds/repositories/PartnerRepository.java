package com.jumboneeds.repositories;

import com.jumboneeds.beans.PartnerBean;
import com.jumboneeds.entities.Partner;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PartnerRepository extends CrudRepository<Partner, String> {

    @Transactional
    Partner findById(@Param("id") String id);

    @Transactional
    @Query(value = "select new com.jumboneeds.beans.PartnerBean(p.id, p.partnerDetail.partnerName, p.partnerDetail.mobileNumber, p.partnerDetail.profilePicUrl) from Partner p where p.partnerDetail.mobileNumber = :mobileNumber")
    PartnerBean findByMobileNumber(@Param("mobileNumber") String mobileNumber);

    @Transactional
    @Query(value = "SELECT p FROM Partner p WHERE p.userName = :userName AND p.password = :password")
    Partner login(@Param("userName") String userName, @Param("password") String password);

    @Transactional
    @Query(value = "SELECT p FROM Partner p WHERE p.buildingName = :buildingName OR p.userName = :userName")
    List<Partner> checkUniquePartner(@Param("buildingName") String buildingName, @Param("userName") String userName);

    @Transactional
    @Query(value = "SELECT p FROM Partner p WHERE p.buildingName = :buildingName")
    List<Partner> checkUniquePartnerBuildingName(@Param("buildingName") String buildingName);

    @Transactional
    @Query(value = "SELECT p FROM Partner p WHERE p.userName = :userName")
    List<Partner> checkUniquePartnerUserName(@Param("userName") String userName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Partner p SET p.parentPartnerId = :parentPartnerId WHERE p.id = :partnerId")
    void updateParentPartner(@Param("partnerId") String partnerId, @Param("parentPartnerId") String parentPartnerId);

}