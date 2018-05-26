package com.jumboneeds.repositories;

import com.jumboneeds.entities.AppConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppConfigRepository extends CrudRepository<AppConfig, String>{

}