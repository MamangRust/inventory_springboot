package com.sanedge.inventoryspringboot.service;

public interface FolderService {

    String createFolder(String folderName);

    void deleteFolder(String folderName);
}