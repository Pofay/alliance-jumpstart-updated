
package com.alliance.jumpstart.repository;

import java.util.List;

import com.alliance.jumpstart.entities.Customer;

import org.springframework.data.repository.CrudRepository;

/**
 * CustomerRepository
 */
public interface CustomerRepository extends CrudRepository<Customer, Long>{

    List<Customer> findByLastName(String lastName);
}