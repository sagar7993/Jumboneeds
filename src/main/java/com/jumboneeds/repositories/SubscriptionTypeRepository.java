package com.jumboneeds.repositories;

import com.jumboneeds.entities.SubscriptionType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SubscriptionTypeRepository extends CrudRepository<SubscriptionType, String> {

    @Transactional
    SubscriptionType findByType(@Param("type") int type);

    @Transactional
    SubscriptionType findById(@Param("id") String id);

}