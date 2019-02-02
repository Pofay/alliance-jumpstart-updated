package com.alliance.jumpstart.services;

import java.time.LocalDateTime;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import io.vavr.control.Try;

public interface StorageService {

    Try init();

    Try<String> store(MultipartFile file, LocalDateTime timeStamp);

    Try<Resource> loadAsResource(String filename);

    void deleteAll();
}