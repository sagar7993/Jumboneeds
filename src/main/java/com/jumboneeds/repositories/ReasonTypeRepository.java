package com.jumboneeds.repositories;

import com.jumboneeds.beans.ReasonTypeBean;
import com.jumboneeds.entities.ReasonType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReasonTypeRepository extends CrudRepository<ReasonType, String> {

    @Transactional
    ReasonType findById(@Param("id") String id);

    @Transactional
    ReasonType findByType(@Param("type") int type);

    @Transactional
    @Query(value = "select new com.jumboneeds.beans.ReasonTypeBean(r.id, r.name, r.type) from ReasonType r")
    List<ReasonTypeBean> fetchAllReasonTypeBeans();
}