package com.jumboneeds.repositories;

import com.jumboneeds.beans.CouponUserBean;
import com.jumboneeds.entities.CouponUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by akash.mercer on 24-Nov-16.
 */
public interface CouponUserRepository extends CrudRepository<CouponUser, String> {

    @Transactional
    @Query(value = "select new com.jumboneeds.beans.CouponUserBean(c.block.blockName, c.flat) from CouponUser c where c.building.id = :buildingId")
    List<CouponUserBean> findByBuilding(@Param("buildingId") String buildingId);
}
