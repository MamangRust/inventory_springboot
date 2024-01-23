package com.sanedge.inventoryspringboot.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String createFileImage(MultipartFile file, String filepath);

    void deleteFileImage(String filepath);
}