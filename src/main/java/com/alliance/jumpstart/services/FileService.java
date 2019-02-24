package com.alliance.jumpstart.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.alliance.jumpstart.entities.FileInfo;
import com.alliance.jumpstart.entities.FileModel;
import com.alliance.jumpstart.entities.JobHiring;

public interface FileService {

	FileModel findById(Long id);
	
}
