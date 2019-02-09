package com.alliance.jumpstart.services;

import com.alliance.jumpstart.entities.User;

import java.util.Collection;

import org.springframework.stereotype.Service;


public interface UserService {

    User save(User user);

    Boolean delete(int id);

    User update(User user);

    User findById(int id);

    User findByUserName(String username);

    User findByEmail(String email);

    Collection<User> findAll();
}
