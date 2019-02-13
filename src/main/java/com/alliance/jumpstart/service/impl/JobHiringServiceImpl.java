package com.alliance.jumpstart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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
    private JobHiringRepository taskRepository;

    @Override
    public JobHiring save(JobHiring task) {
        return taskRepository.save(task);
    }

    @Override
    public Boolean delete(int id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public JobHiring update(JobHiring task) {
        return taskRepository.save(task);
    }

    @Override
    public JobHiring findById(int id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public List<JobHiring> findAll() {
        return (List<JobHiring>) taskRepository.findAll();
    }

    @Override
    public List<JobHiring> findByStatus(String status) {
        return taskRepository.findByStatus(status);
    }

    @Override
    public List<JobHiring> findByUserIdStatus(int userId, String status) {
        //return  taskRepository.findByUserIdStatus(userId, status);
        return  taskRepository.findByUserIdAndStatus(userId, status);
    }

    @Override
    public List<JobHiring> findBetween(int start, int end) {
        return taskRepository.findBetween(start, end);
    }
}
