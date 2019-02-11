package com.alliance.jumpstart.services;

import org.springframework.data.jpa.repository.Query;
import com.alliance.jumpstart.entities.JobHiring;

import java.util.List;


public interface JobHiringService {

    JobHiring save(JobHiring task);

    Boolean delete(int id);

    JobHiring update(JobHiring task);

    JobHiring findById(int id);

    List<JobHiring> findAll();

    List<JobHiring> findByStatus(String status);

    List<JobHiring> findByUserIdStatus(int userId, String status);

    List<JobHiring> findBetween(int start, int end);
    
    

}