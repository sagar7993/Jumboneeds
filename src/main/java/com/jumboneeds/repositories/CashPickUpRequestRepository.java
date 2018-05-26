package com.jumboneeds.repositories;

import com.jumboneeds.entities.CashPickUpRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashPickUpRequestRepository extends CrudRepository<CashPickUpRequest, String> {

}