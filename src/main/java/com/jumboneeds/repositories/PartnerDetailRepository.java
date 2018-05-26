package com.jumboneeds.repositories;

import com.jumboneeds.entities.PartnerDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PartnerDetailRepository extends CrudRepository<PartnerDetail, String> {

    @Transactional
    PartnerDetail findById(@Param("id") String id);

    @Transactional
    PartnerDetail findByMobileNumber(@Param("mobileNumber") String mobileNumber);

}