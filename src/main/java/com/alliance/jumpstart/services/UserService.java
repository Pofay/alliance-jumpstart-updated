package com.alliance.jumpstart.services;

import com.alliance.jumpstart.entities.JobHiring;
import com.alliance.jumpstart.entities.User;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User save(User user);

    Boolean delete(int id);

    User update(User user);

    User findById(int id);

    User findByUserName(String username);

    User findByEmail(String email);
    
    User findByStatus(boolean stat);
    
    List<User> findByUser(String user);
   
    Collection<User> findAll();
}
