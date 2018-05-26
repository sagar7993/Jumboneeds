package com.jumboneeds.repositories;

import com.jumboneeds.entities.StockType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StockTypeRepository extends CrudRepository<StockType, String> {

    @Transactional
    StockType findByType(@Param("type") int type);

}