package com.jumboneeds.repositories;

import com.jumboneeds.beans.BillingLogBean;
import com.jumboneeds.entities.BillingLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface BillingLogRepository extends CrudRepository<BillingLog, String> {

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.BillingLogBean(b.id, b.amount, b.date, b.cashback, b.description, b.previousBalance, b.billingLogType.type, b.paymentType.type, pm.productImageUrl) FROM BillingLog b LEFT JOIN b.subscription s ON s.id = b.subscription LEFT JOIN s.product p ON p.id = s.product LEFT JOIN p.productMaster pm ON pm.id = p.productMaster WHERE b.user.id = :userId ORDER BY b.createdAt DESC, b.billingLogType.type DESC")
    List<BillingLogBean> findByUser(@Param("userId") String userId);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.BillingLogBean(b.id, b.amount, b.date, b.cashback, b.description, b.previousBalance, b.billingLogType.type, b.paymentType.type, pm.productImageUrl) FROM BillingLog b LEFT JOIN b.subscription s ON s.id = b.subscription LEFT JOIN s.product p ON p.id = s.product LEFT JOIN p.productMaster pm ON pm.id = p.productMaster WHERE b.user.id = :userId AND b.date between :minDate and :maxDate ORDER BY b.createdAt DESC")
    List<BillingLogBean> getUserBillingLogsForSchedule(@Param("userId") String userId, @Param("minDate") Date minDate, @Param("maxDate") Date maxDate);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.BillingLogBean(b.id, b.amount, b.date, b.cashback, b.description, b.previousBalance, b.billingLogType.type, b.paymentType.type, pm.productImageUrl) FROM BillingLog b LEFT JOIN b.subscription s ON s.id = b.subscription LEFT JOIN s.product p ON p.id = s.product LEFT JOIN p.productMaster pm ON pm.id = p.productMaster WHERE b.user.id = :userId AND b.date >= :startDate AND b.date <= :endDate AND b.billingLogType.type <> 2 ORDER BY b.createdAt DESC")
    List<BillingLog> getUserBillingLogs(@Param("userId") String userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.BillingLogBean(b.id, b.amount, b.date, b.cashback, b.description, b.previousBalance, b.billingLogType.type, b.paymentType.type, pm.productImageUrl) FROM BillingLog b LEFT JOIN b.subscription s ON s.id = b.subscription LEFT JOIN s.product p ON p.id = s.product LEFT JOIN p.productMaster pm ON pm.id = p.productMaster WHERE b.user.id = :userId AND b.date >= :startDate AND b.date <= :endDate ORDER BY b.createdAt DESC")
    List<BillingLog> getAllUserBillingLogsBetweenDates(@Param("userId") String userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}