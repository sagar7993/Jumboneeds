package com.jumboneeds.repositories;

import com.jumboneeds.beans.SubscriptionExceptionBean;
import com.jumboneeds.entities.Subscription;
import com.jumboneeds.entities.SubscriptionException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface SubscriptionExceptionRepository extends CrudRepository<SubscriptionException, String> {

    @Transactional
    SubscriptionException findById(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE SubscriptionException s SET s.productQuantity = :productQuantity WHERE s.id = :id")
    void update(@Param("id") String id, @Param("productQuantity") Integer productQuantity);

    @Transactional
    @Query(value = "SELECT s FROM SubscriptionException s WHERE s.subscription IN :subscriptions AND s.date = :date order by s.createdAt desc")
    List<SubscriptionException> findBySubscriptionsAndDate(@Param("subscriptions") List<Subscription> subscriptions, @Param("date") Date date);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.SubscriptionExceptionBean(s.id, s.date, s.productQuantity, s.subscription.id) FROM SubscriptionException s WHERE s.subscription.id IN :subscriptionIds AND s.date = :date order by s.createdAt desc")
    List<SubscriptionExceptionBean> findSubscriptionExceptionBeansBySubscriptionsAndDate(@Param("subscriptionIds") List<String> subscriptionIds, @Param("date") Date date);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.SubscriptionExceptionBean(s.id, s.date, s.productQuantity, s.subscription.id) FROM SubscriptionException s WHERE s.user.id = :userId AND s.date >= :date")
    List<SubscriptionExceptionBean> findScheduledSubscriptionExceptionsByUser(@Param("userId") String userId, @Param("date") Date date);

}