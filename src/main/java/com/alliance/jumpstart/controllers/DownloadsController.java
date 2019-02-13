package com.alliance.jumpstart.controllers;

import javax.servlet.http.HttpServletRequest;

import com.alliance.jumpstart.services.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.vavr.control.Try;

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

        MediaType contentType = Try.of(() -> request.getServletContext().getMimeType(r.getFile().getAbsolutePath()))
                .map(MediaType::parseMediaType).getOrElse(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok().contentType(contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + r.getFilename() + "\"").body(r);
    }
}