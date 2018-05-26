package com.jumboneeds.repositories;

import com.jumboneeds.entities.CreManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreManagerRepository extends CrudRepository<CreManager, String> {

}