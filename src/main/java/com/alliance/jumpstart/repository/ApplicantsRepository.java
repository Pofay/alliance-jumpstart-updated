package com.alliance.jumpstart.repository;

import com.alliance.jumpstart.entities.Applicant;
import com.alliance.jumpstart.entities.Career;

import org.springframework.data.repository.CrudRepository;

/**
 * CareersRepository
 */
public interface ApplicantsRepository extends CrudRepository<Applicant, Long> {
}