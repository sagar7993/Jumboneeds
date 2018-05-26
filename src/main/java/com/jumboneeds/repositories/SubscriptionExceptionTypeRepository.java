package com.jumboneeds.repositories;

import com.jumboneeds.entities.SubscriptionExceptionType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SubscriptionExceptionTypeRepository extends CrudRepository<SubscriptionExceptionType, String> {

    @Transactional
    SubscriptionExceptionType findById(@Param("id") String id);

    @Transactional
    SubscriptionExceptionType findByType(@Param("type") int type);

}