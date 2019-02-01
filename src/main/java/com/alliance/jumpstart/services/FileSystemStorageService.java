package com.alliance.jumpstart.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import io.vavr.control.Try;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(FileStorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public Try store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file " + filename);
        }
        if (filename.contains("..")) {
            // This is a security check
            throw new RuntimeException("Cannot store file with relative path outside current directory " + filename);
        }
        return Try.withResources(() -> file.getInputStream()).of((stream) -> Files.copy(stream,
                this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING));
    }

    private Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Try<Resource> loadAsResource(String filename) {
        return Try.of(() -> load(filename)).flatMap(f -> Try.of(() -> new UrlResource(f.toUri())));
    }

    @Override
    public Try init() {
        return Try.of(() -> Files.createDirectories(rootLocation));
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}