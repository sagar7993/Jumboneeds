package com.jumboneeds.repositories;

import com.jumboneeds.beans.FilteredSubscriptionBean;
import com.jumboneeds.beans.SubscriptionBean;
import com.jumboneeds.entities.Product;
import com.jumboneeds.entities.Subscription;
import com.jumboneeds.entities.SubscriptionType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, String> {

    @Transactional
    Subscription findById(@Param("id") String id);

    @Modifying
    @Transactional
    @Query("UPDATE Subscription s SET s.startDate = :startDate, s.endDate = :endDate, s.productQuantity = :productQuantity, s.subscriptionType = :subscriptionType WHERE s.id = :id")
    void updateSubscription(@Param("id") String id, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("productQuantity") int productQuantity, @Param("subscriptionType")SubscriptionType subscriptionType);

    @Modifying
    @Transactional
    @Query("UPDATE Subscription s SET s.endDate = :endDate WHERE s.id = :id")
    void endSubscription(@Param("id") String id, @Param("endDate") Date endDate);

    //This API won't return flash sale subscriptions.
    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.SubscriptionBean(s.id, s.startDate, s.endDate, s.productQuantity, s.subscriptionType.type, s.subscriptionType.name, s.product.id, s.product.productMaster.productName, s.product.productMaster.productImageUrl, s.product.productUnitPrice, s.product.productMaster.productUnitSize) FROM Subscription s WHERE s.user.id = :userId AND s.endDate > :date and s.endDate > s.startDate and s.flashSaleProduct = 0")
    List<SubscriptionBean> findActiveSubscriptionBeansByUser(@Param("userId") String userId, @Param("date") Date date);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.SubscriptionBean(s.id, s.startDate, s.endDate, s.productQuantity, s.subscriptionType.type, s.subscriptionType.name, s.product.id, s.product.productMaster.productName, s.product.productMaster.productImageUrl, s.product.productUnitPrice, s.product.productMaster.productUnitSize) FROM Subscription s WHERE s.user.id = :userId AND s.endDate >= :date and s.endDate > s.startDate")
    List<SubscriptionBean> findScheduledSubscriptionByUser(@Param("userId") String userId, @Param("date") Date date);

    @Transactional
    @Query(value = "SELECT s FROM Subscription s WHERE s.user.id IN :userIds AND s.startDate <= :date AND s.endDate >= :date and s.endDate > s.startDate")
    List<Subscription> findActiveSubscriptionsByUserList(@Param("userIds") List<String> userIds, @Param("date") Date date);

    @Transactional
    @Query(value = "SELECT s FROM Subscription s WHERE s.product IN :products AND s.startDate <= :date AND s.endDate >= :date and s.endDate > s.startDate")
    List<Subscription> findByProductsAndDate(@Param("products")List<Product> products, @Param("date") Date date);

    @Transactional
    @Query(value = "SELECT s FROM Subscription s WHERE s.product IN :products AND s.startDate <= :date AND s.endDate >= :date and s.endDate > s.startDate AND s.product.fulfilledByPartner = false")
    List<Subscription> findByProductsAndDateWithoutPartnerProducts(@Param("products")List<Product> products, @Param("date") Date date);

    @Transactional
    @Query(value = "SELECT s FROM Subscription s WHERE s.startDate <= :date AND s.endDate >= :date and s.endDate > s.startDate")
    List<Subscription> fetchActiveSubscriptionsByDate(@Param("date") Date date);

    @Transactional
    @Query(value = "SELECT s FROM Subscription s WHERE s.startDate <= :date AND s.endDate >= :date and s.endDate > s.startDate AND s.product.fulfilledByPartner = false")
    List<Subscription> fetchActiveSubscriptionsByDateWithoutPartnerProducts(@Param("date") Date date);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.FilteredSubscriptionBean(s.id, s.startDate, s.endDate, s.product.id, s.product.productMaster.productName, s.product.productMaster.productAlias, s.product.productMaster.productSubCategory.productSubCategoryName, s.product.productMaster.productSubCategory.type, s.product.productMaster.productSubCategory.productCategory.productCategoryName, s.product.productMaster.productSubCategory.productCategory.type, s.product.productMaster.productUnitSize, s.product.productMaster.productImageUrl, s.product.productUnitPrice, s.productUnitPrice, s.productQuantity, s.product.fulfilledByPartner, s.subscriptionType.type, s.flashSaleProduct, s.user.id, s.user.userName, s.user.block.blockName, s.user.flat, s.user.building.buildingName) " +
            "FROM Subscription s WHERE s.user.id = :userId AND s.startDate <= :date AND s.endDate >= :date and s.endDate > s.startDate")
    List<FilteredSubscriptionBean> findSubscriptionBeansByUser(@Param("userId") String userId, @Param("date") Date date);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.FilteredSubscriptionBean(s.id, s.startDate, s.endDate, s.product.id, s.product.productMaster.productName, s.product.productMaster.productAlias, s.product.productMaster.productSubCategory.productSubCategoryName, s.product.productMaster.productSubCategory.type, s.product.productMaster.productSubCategory.productCategory.productCategoryName, s.product.productMaster.productSubCategory.productCategory.type, s.product.productMaster.productUnitSize, s.product.productMaster.productImageUrl, s.product.productUnitPrice, s.productUnitPrice, s.productQuantity, s.product.fulfilledByPartner, s.subscriptionType.type, s.flashSaleProduct, s.user.id, s.user.userName, s.user.block.blockName, s.user.flat, s.user.building.buildingName) " +
            "FROM Subscription s WHERE s.product IN :products AND s.startDate <= :date AND s.endDate >= :date and s.endDate > s.startDate")
    List<FilteredSubscriptionBean> findSubscriptionBeansByProductsAndDate(@Param("products")List<Product> products, @Param("date") Date date);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.FilteredSubscriptionBean(s.id, s.startDate, s.endDate, s.product.id, s.product.productMaster.productName, s.product.productMaster.productAlias, s.product.productMaster.productSubCategory.productSubCategoryName, s.product.productMaster.productSubCategory.type, s.product.productMaster.productSubCategory.productCategory.productCategoryName, s.product.productMaster.productSubCategory.productCategory.type, s.product.productMaster.productUnitSize, s.product.productMaster.productImageUrl, s.product.productUnitPrice, s.productUnitPrice, s.productQuantity, s.product.fulfilledByPartner, s.subscriptionType.type, s.flashSaleProduct, s.user.id, s.user.userName, s.user.block.blockName, s.user.flat, s.user.building.buildingName) " +
            "FROM Subscription s WHERE s.product IN :products AND s.startDate <= :date AND s.endDate >= :date and s.endDate > s.startDate AND s.product.fulfilledByPartner = false")
    List<FilteredSubscriptionBean> findSubscriptionBeansByProductsAndDateWithoutPartnerProducts(@Param("products")List<Product> products, @Param("date") Date date);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.FilteredSubscriptionBean(s.id, s.startDate, s.endDate, s.product.id, s.product.productMaster.productName, s.product.productMaster.productAlias, s.product.productMaster.productSubCategory.productSubCategoryName, s.product.productMaster.productSubCategory.type, s.product.productMaster.productSubCategory.productCategory.productCategoryName, s.product.productMaster.productSubCategory.productCategory.type, s.product.productMaster.productUnitSize, s.product.productMaster.productImageUrl, s.product.productUnitPrice, s.productUnitPrice, s.productQuantity, s.product.fulfilledByPartner, s.subscriptionType.type, s.flashSaleProduct, s.user.id, s.user.userName, s.user.block.blockName, s.user.flat, s.user.building.buildingName) " +
            "FROM Subscription s WHERE s.startDate <= :date AND s.endDate >= :date and s.endDate > s.startDate")
    List<FilteredSubscriptionBean> fetchSubscriptionBeansByDate(@Param("date") Date date);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.FilteredSubscriptionBean(s.id, s.startDate, s.endDate, s.product.id, s.product.productMaster.productName, s.product.productMaster.productAlias, s.product.productMaster.productSubCategory.productSubCategoryName, s.product.productMaster.productSubCategory.type, s.product.productMaster.productSubCategory.productCategory.productCategoryName, s.product.productMaster.productSubCategory.productCategory.type, s.product.productMaster.productUnitSize, s.product.productMaster.productImageUrl, s.product.productUnitPrice, s.productUnitPrice, s.productQuantity, s.product.fulfilledByPartner, s.subscriptionType.type, s.flashSaleProduct, s.user.id, s.user.userName, s.user.block.blockName, s.user.flat, s.user.building.buildingName) " +
            "FROM Subscription s WHERE s.startDate <= :date AND s.endDate >= :date and s.endDate > s.startDate AND s.product.fulfilledByPartner = false")
    List<FilteredSubscriptionBean> fetchSubscriptionBeansByDateWithoutPartnerProducts(@Param("date") Date date);


}