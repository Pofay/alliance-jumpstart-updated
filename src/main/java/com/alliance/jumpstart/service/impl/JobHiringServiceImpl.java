package com.alliance.jumpstart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.alliance.jumpstart.entities.JobHiring;
import com.alliance.jumpstart.repository.JobHiringRepository;
import com.alliance.jumpstart.services.JobHiringService;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class JobHiringServiceImpl implements JobHiringService {
	
	
	@Autowired
    private JdbcTemplate jtm;

    @Override
    public List<JobHiring> findByStatus(String stat) {

        String sql = "SELECT * FROM task where Status='"+stat+"' ";

        List<JobHiring> jh = jtm.query(sql, new BeanPropertyRowMapper(JobHiring.class));

        return jh;
    }

    @Autowired
    private JobHiringRepository jobRepository;

    @Override
    public JobHiring save(JobHiring task) {
        return jobRepository.save(task);
    }

    @Override
    public Boolean delete(int id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public JobHiring update(JobHiring task) {
        return jobRepository.save(task);
    }

    @Override
    public JobHiring findById(int id) {
        return jobRepository.findById(id).get();
    }

    @Override
    public List<JobHiring> findAll() {
        return (List<JobHiring>) jobRepository.findAll();
    }

   /* @Override
    public List<JobHiring> findByStatus(String status) {
        return taskRepository.findByStatus(status);
    }*/

    @Override
    public List<JobHiring> findByUserIdStatus(int userId, String status) {
        //return  taskRepository.findByUserIdStatus(userId, status);
        return  jobRepository.findByUserIdAndStatus(userId, status);
    }

    @Override
    public List<JobHiring> findBetween(int start, int end) {
        return jobRepository.findBetween(start, end);
    }
}
