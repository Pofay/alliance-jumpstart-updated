package com.alliance.jumpstart.controllers;

import com.alliance.jumpstart.entities.Career;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import com.alliance.jumpstart.entities.Applicant;

import com.alliance.jumpstart.repository.CareersRepository;
import com.alliance.jumpstart.services.StorageService;
import com.alliance.jumpstart.viewmodels.ApplicantDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DownloadsController {

    private StorageService service;

    @Autowired
    public DownloadsController(StorageService service) {
        this.service = service;
    }

    @GetMapping("/resumes/{fileName}")
    public ResponseEntity<Resource> downloadResume(@PathVariable String fileName, HttpServletRequest request) {
        Resource r = service.loadAsResource(fileName).getOrElseThrow(() -> new RuntimeException());

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(r.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + r.getFilename() + "\"")
                .body(r);
    }
}