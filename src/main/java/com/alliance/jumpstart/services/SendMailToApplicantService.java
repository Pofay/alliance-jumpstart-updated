package com.alliance.jumpstart.services;

import java.util.List;


import com.alliance.jumpstart.entities.SendMailToApplicant;

public interface SendMailToApplicantService {

	
	SendMailToApplicant save(SendMailToApplicant smta);


    //SendMailToApplicant update(SendMailToApplicant smta);

    SendMailToApplicant updateStatus(boolean smta);
    
    SendMailToApplicant findById(int id);

    List<SendMailToApplicant> findAll();

    //List<SendMailToApplicant> findByStatus(String status);

  
    
}
