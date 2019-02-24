package com.alliance.jumpstart.service.impl;

import com.alliance.jumpstart.entities.JobHiring;
import com.alliance.jumpstart.entities.User;
import com.alliance.jumpstart.repository.UserRepository;
import com.alliance.jumpstart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    
    @Autowired
    private JdbcTemplate jtm;

    @Override
    public List<User> findByUser(String username) {

        String sql = "SELECT status FROM user where username ='"+username+"' ";

        List<User> jh = jtm.query(sql, new BeanPropertyRowMapper(User.class));

        return jh;
    }
    
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean delete(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public User findByStatus(boolean id) {
        return userRepository.findByStatus(id);
    }

    @Override
    public Collection<User> findAll() {
        Iterable<User> itr = userRepository.findAll();
        return (Collection<User>) itr;
    }
}