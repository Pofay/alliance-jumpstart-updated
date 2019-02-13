package com.alliance.jumpstart.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import io.vavr.control.Try;
import io.vavr.control.Validation;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(FileStorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public Try<String> store(MultipartFile file, LocalDateTime timestamp) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        return Validation.combine(isNotEmpty(file, fileName), verifyAsDocOrPdf(fileName), isNotRelative(fileName))
                .ap((f, extension, filePath) -> new StoragePayload(f, extension, timestamp.toString())).toTry()
                .flatMap((payload) -> Try
                        .withResources(() -> payload.file.getInputStream()).of((stream) -> Files.copy(stream,
                                this.rootLocation.resolve(payload.newFileName), StandardCopyOption.REPLACE_EXISTING))
                        .map(o -> payload.newFileName));
    }

    private Validation<String, MultipartFile> isNotEmpty(MultipartFile f, String cleanFileName) {
        return f.isEmpty() ? Validation.invalid("Failed to store empty file: " + cleanFileName) : Validation.valid(f);
    }

    private Validation<String, String> verifyAsDocOrPdf(String fileName) {
        return StringUtils.getFilenameExtension(fileName).equals("doc")
                || StringUtils.getFilenameExtension(fileName).equals("pdf")
                || StringUtils.getFilenameExtension(fileName).equals("docx")
                        ? Validation.valid(StringUtils.getFilenameExtension(fileName))
                        : Validation.invalid(fileName + " is not a .doc or .pdf");

    }

    private Validation<String, String> isNotRelative(String fileName) {
        return fileName.contains("..")
                ? Validation.invalid("File cannot be stored since it has directory directives: " + fileName)
                : Validation.valid(fileName);
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