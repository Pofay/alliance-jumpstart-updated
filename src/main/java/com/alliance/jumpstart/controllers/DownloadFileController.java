package com.alliance.jumpstart.controllers;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.alliance.jumpstart.entities.Career;
import com.alliance.jumpstart.entities.FileInfo;
import com.alliance.jumpstart.entities.FileModel;
import com.alliance.jumpstart.repository.FileRepository;
import com.alliance.jumpstart.controllers.DownloadFileController;

@Controller
public class DownloadFileController {

	@Autowired
	FileRepository fileRepository;
	
	/*
	 * Retrieve Files' Information
	 */
	
	 
	
	@GetMapping("/files")
	public String getListFiles(Model model) {
		List<FileInfo> fileInfos = fileRepository.findAll().stream().map(
				fileModel ->	{
					
					String applicantname = fileModel.getApplicantName();
					String email = fileModel.getEmail();
					String position = fileModel.getPosition();
					String message = fileModel.getMessage();
					String filename = fileModel.getName();
					String url = MvcUriComponentsBuilder.fromMethodName(DownloadFileController.class,
	                        "downloadFile", fileModel.getName().toString()).build().toString();
					return new FileInfo(applicantname,email,position,message,filename, url); 
				} 
			)
			.collect(Collectors.toList());
	
		model.addAttribute("files", fileInfos);
		return "dashboard/resumebank";
	}
 
    /*
     * Download Files
     */
	@GetMapping("/files/{filename}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) {
		FileModel file = fileRepository.findByName(filename);
		return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
					.body(file.getPic());	
	}
}