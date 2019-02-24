package com.alliance.jumpstart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alliance.jumpstart.entities.FileModel;
import com.alliance.jumpstart.repository.FileRepository;
import com.alliance.jumpstart.repository.JobHiringRepository;
import com.alliance.jumpstart.services.FileService;
import com.alliance.jumpstart.services.JobHiringService;

@Service("fileService")
public class FileServiceImpl  implements FileService{

	@Autowired
    private FileRepository fileRepository;
	
	@Override
	public FileModel findById(Long id) {
		// TODO Auto-generated method stub
		return fileRepository.findById(id).get();
	}


}
