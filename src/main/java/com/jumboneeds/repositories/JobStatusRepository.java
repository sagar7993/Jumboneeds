package com.jumboneeds.repositories;

import com.jumboneeds.entities.JobStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobStatusRepository extends CrudRepository<JobStatus, String> {

}