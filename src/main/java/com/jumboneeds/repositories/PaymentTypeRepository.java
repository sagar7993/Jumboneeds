package com.jumboneeds.repositories;

import com.jumboneeds.entities.PaymentType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PaymentTypeRepository extends CrudRepository<PaymentType, String> {

    @Transactional
    PaymentType findByType(@Param("type") int type);

    @Transactional
    PaymentType findById(@Param("id") String id);

}