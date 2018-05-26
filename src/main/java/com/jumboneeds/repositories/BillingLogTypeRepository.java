package com.jumboneeds.repositories;

import com.jumboneeds.entities.BillingLogType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BillingLogTypeRepository extends CrudRepository<BillingLogType, String> {

    @Transactional
    BillingLogType findByType(@Param("type") int type);

    @Transactional
    BillingLogType findById(@Param("id") String id);

}