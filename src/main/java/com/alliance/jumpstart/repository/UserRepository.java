package com.alliance.jumpstart.repository;

import com.alliance.jumpstart.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String email);
}
