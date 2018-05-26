package com.jumboneeds.repositories;

import com.jumboneeds.beans.BillingMasterBean;
import com.jumboneeds.beans.LowBalanceUserBean;
import com.jumboneeds.beans.TransactionBean;
import com.jumboneeds.entities.BillingMaster;
import com.jumboneeds.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface BillingMasterRepository extends CrudRepository<BillingMaster, String> {

    @Transactional
    BillingMaster findById(@Param("id") String id);

    @Transactional
    @Query(value = "SELECT b.amount FROM BillingMaster b WHERE b.id = :id")
    Double getAmountForBillingMaster(@Param("id") String id);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.BillingMasterBean(b.user.id, b.amount, b.bankTxnId) FROM BillingMaster b WHERE b.user.id = :userId and b.current = true")
    BillingMasterBean findByUser(@Param("userId") String userId);

    @Transactional
    @Query(value = "SELECT b FROM BillingMaster b WHERE b.current = true")
    List<BillingMaster> fetchCurrentBillingMasters();

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.BillingMasterBean(b.user.id, b.amount, b.bankTxnId) FROM BillingMaster b WHERE b.current = true")
    List<BillingMasterBean> fetchCurrentBillingMasterBeans();

    @Transactional
    @Query(value = "SELECT b FROM BillingMaster b WHERE b.user IN :users AND b.paymentDate = :date AND b.current = false")
    List<BillingMaster> findByDateAndUserList(@Param("users") List<User> users, @Param("date") Date date);

    @Transactional
    @Query(value = "select new com.jumboneeds.beans.LowBalanceUserBean(b.amount, b.user.fcmAndroidToken) from BillingMaster b where b.amount <= :amount and b.current = true")
    List<LowBalanceUserBean> findByLowAmount(@Param("amount") Double amount);

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.TransactionBean(b.amount, b.bankTxnId, b.paymentDate, b.paymentType.name, b.paymentType.type) FROM BillingMaster b WHERE b.user.id = :userId and b.paymentType.type between 1 and 3 order by b.paymentDate desc")
    List<TransactionBean> getTransactionHistory(@Param("userId") String userId);
}