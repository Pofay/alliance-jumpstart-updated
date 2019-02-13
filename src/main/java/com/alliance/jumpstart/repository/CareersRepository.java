package com.alliance.jumpstart.repository;

import com.alliance.jumpstart.entities.Career;



import org.springframework.data.repository.CrudRepository;

/**
 * CareersRepository
 */
public interface CareersRepository extends CrudRepository<Career, Long> {

	public Career findByPosition(String position);
	
}