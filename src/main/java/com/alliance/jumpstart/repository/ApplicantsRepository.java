package com.alliance.jumpstart.repository;

import java.util.Optional;

import com.alliance.jumpstart.entities.Applicant;

import org.springframework.data.repository.CrudRepository;

/**
 * CareersRepository
 */
public interface ApplicantsRepository extends CrudRepository<Applicant, Long> {

    Optional<Applicant> findByEmail(String email);
}