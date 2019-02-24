package com.alliance.jumpstart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alliance.jumpstart.entities.Applicant;
import com.alliance.jumpstart.repository.ApplicantsRepository;
import com.alliance.jumpstart.services.ApplicantService;

@Service
public class ApplicantServiceImpl implements ApplicantService{

	 @Autowired
	    private ApplicantsRepository applicantRepository;
	
	 @Override
	    public Applicant saveApplicant(Applicant app) {
	        return applicantRepository.save(app);
	    }
	 
	 @Override
	    public Applicant findById(Long id) {
		 return applicantRepository.findById(id).get();
	    }
}
