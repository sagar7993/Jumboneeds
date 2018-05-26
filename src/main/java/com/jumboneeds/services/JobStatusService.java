package com.jumboneeds.services;

import com.jumboneeds.entities.JobStatus;
import com.jumboneeds.repositories.JobStatusRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobStatusService {
    private static final String TAG = "JobStatusService : ";

    @Autowired
    private JobStatusRepository jobStatusRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public void saveJob(JobStatus jobStatus) {
        jobStatusRepository.save(jobStatus);
    }

    public List<JobStatus> fetchAll() {
        return (List<JobStatus>) jobStatusRepository.findAll();
    }

}