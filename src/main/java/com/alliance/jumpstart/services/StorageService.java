package com.alliance.jumpstart.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import io.vavr.control.Try;

public interface StorageService {

    Try init();

    Try store(MultipartFile file);

    Try<Resource> loadAsResource(String filename);

    void deleteAll();
}