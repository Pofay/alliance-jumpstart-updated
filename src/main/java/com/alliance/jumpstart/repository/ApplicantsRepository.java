package com.alliance.jumpstart.repository;

import com.alliance.jumpstart.entities.Applicant;
import com.alliance.jumpstart.entities.Career;
import com.alliance.jumpstart.entities.JobHiring;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * CareersRepository
 */
public interface ApplicantsRepository extends CrudRepository<Applicant, Long> {
	
	
	 
}