package com.alliance.jumpstart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alliance.jumpstart.entities.FileModel;

public interface FileRepository extends JpaRepository<FileModel, Long>{	
	public FileModel findByName(String name);
	
}