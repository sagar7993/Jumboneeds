package com.jumboneeds.repositories;

import com.jumboneeds.beans.BlockBean;
import com.jumboneeds.entities.Block;
import com.jumboneeds.entities.Building;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by akash.mercer on 17-Oct-16.
 */

@Repository
public interface BlockRepository extends CrudRepository<Block, String> {

    @Transactional
    Block findById(@Param("id") String id);

    @Transactional
    @Query(value = "select new com.jumboneeds.beans.BlockBean(b.id, b.blockName) from Block b where b.building = :building and b.active = true")
    List<BlockBean> findByBuilding(@Param("building") Building building);

    @Transactional
    Long countByBuilding(@Param("building") Building building);
}
