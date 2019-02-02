package com.alliance.jumpstart.services;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Customer
 */

public class StoragePayload {

    public MultipartFile file;
    public String newFileName;

    public StoragePayload(MultipartFile file, String extension, String newFileName) {
        this.file = file;
        this.newFileName = String.format("%s.%s", newFileName, extension);
    }

}