package com.jumboneeds.repositories;

import com.jumboneeds.entities.Block;
import com.jumboneeds.entities.Building;
import com.jumboneeds.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    @Transactional
    User findById(@Param("id") String id);

    @Transactional
    User findFirstByMobileNumber(@Param("mobileNumber") String mobileNumber);

    @Transactional
    User findFirstByEmail(@Param("email") String email);

    @Transactional
    List<User> findByBlock(@Param("block") Block block);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.billingMasterId = :billingMasterId, u.status = 1 WHERE u.id = :id")
    void updateBillingMasterId(@Param("billingMasterId") String billingMasterId, @Param("id") String id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.flat = :flat, u.block = :block, u.building = :building, u.status = 2 WHERE u.id = :id")
    void updateBuildingAndFlat(@Param("flat") String flat, @Param("block") Block block, @Param("building") Building building, @Param("id") String id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.mobileNumber = :mobileNumber, u.email = :email WHERE u.id = :id")
    void updateProfile(@Param("mobileNumber") String mobileNumber, @Param("email") String email, @Param("id") String id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.mobileNumber = :mobileNumber, u.email = :email, u.userName = :userName, u.flat = :flat WHERE u.id = :id")
    void updateProfile(@Param("mobileNumber") String mobileNumber, @Param("email") String email, @Param("userName") String userName, @Param("flat") String flat, @Param("id") String id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.appVersion = :appVersion, u.deviceType = :deviceType, u.fcmAndroidToken = :fcmAndroidToken WHERE u.id = :id")
    void updateDeviceAndVersion(@Param("appVersion") String appVersion, @Param("deviceType") String deviceType, @Param("fcmAndroidToken") String fcmAndroidToken, @Param("id") String id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.fcmAndroidToken = :fcmAndroidToken WHERE u.id = :id")
    void updateFcmAndroidToken(@Param("fcmAndroidToken") String fcmAndroidToken, @Param("id") String id);

    @Transactional
    @Query(value = "SELECT u FROM User u WHERE u.building IN :buildings ORDER BY u.building.buildingName ASC, u.flat ASC")
    List<User> findByBuildings(@Param("buildings") List<Building> buildings);

    @Transactional
    @Query(value = "SELECT u FROM User u, BillingMaster bm WHERE u.billingMasterId = bm.id AND bm.current = true AND bm.amount < u.building.minimumAllowedAmount")
    List<User> findUsersWithBalanceBelowMinimumBuildingAmount();

}