package com.alliance.jumpstart.services;

import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import io.vavr.control.Try;

public interface StorageService {

    Try init();

    Try<String> store(MultipartFile file, UUID identifier);

    Try<Resource> loadAsResource(String filename);

    void deleteAll();
}