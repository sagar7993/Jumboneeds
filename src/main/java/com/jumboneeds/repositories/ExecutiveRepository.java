package com.jumboneeds.repositories;

import com.jumboneeds.beans.ExecutiveBean;
import com.jumboneeds.entities.Executive;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ExecutiveRepository extends CrudRepository<Executive, String> {

    @Transactional
    @Query(value = "select e from Executive e where e.id = :id and e.active = true ")
    Executive findById(@Param("id") String id);

    @Transactional
    @Query(value = "select new com.jumboneeds.beans.ExecutiveBean(e.id, e.executiveName, e.mobileNumber, e.profilePicUrl) from Executive e where e.mobileNumber = :mobileNumber and e.active = true ")
    ExecutiveBean findByMobileNumber(@Param("mobileNumber") String mobileNumber);
}