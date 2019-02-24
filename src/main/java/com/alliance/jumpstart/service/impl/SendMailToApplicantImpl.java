package com.alliance.jumpstart.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alliance.jumpstart.entities.JobHiring;
import com.alliance.jumpstart.entities.SendMailToApplicant;
import com.alliance.jumpstart.repository.JobHiringRepository;
import com.alliance.jumpstart.repository.SendMailToApplicantRepository;
import com.alliance.jumpstart.services.SendMailToApplicantService;

@Service
@Transactional
public class SendMailToApplicantImpl implements SendMailToApplicantService{
	
	
	@Autowired
    private JdbcTemplate jtm;
	
	  @Autowired
	    private SendMailToApplicantRepository sendMailRepository;


    @Override
    public List<SendMailToApplicant> findAll() {

        String sql = "SELECT * FROM  table_sendemail ";

        List<SendMailToApplicant> jh = jtm.query(sql, new BeanPropertyRowMapper(SendMailToApplicant.class));

        return jh;
    }
    
   

	@Override
	public SendMailToApplicant save(SendMailToApplicant smta) {
		// TODO Auto-generated method stub
		return sendMailRepository.save(smta);
	}

	@Override
	public SendMailToApplicant updateStatus(boolean smta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SendMailToApplicant findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
