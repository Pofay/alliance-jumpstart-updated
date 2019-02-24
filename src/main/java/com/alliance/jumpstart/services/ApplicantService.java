package com.alliance.jumpstart.services;

import com.alliance.jumpstart.entities.Applicant;
import com.alliance.jumpstart.entities.JobHiring;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public interface ApplicantService {

	Applicant saveApplicant(Applicant applicant);
	Applicant findById(Long id);
}
