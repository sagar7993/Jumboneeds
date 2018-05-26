package com.jumboneeds.repositories;

import com.jumboneeds.entities.BannerType;
import com.jumboneeds.entities.SubscriptionType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BannerTypeRepository extends CrudRepository<BannerType, String> {

    @Transactional
    SubscriptionType findByType(@Param("type") int type);

}