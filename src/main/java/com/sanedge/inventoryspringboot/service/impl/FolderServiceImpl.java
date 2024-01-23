package com.sanedge.inventoryspringboot.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;

import com.sanedge.inventoryspringboot.service.FolderService;

@Service
public class FolderServiceImpl implements FolderService {
    public String createFolder(String name) {
        String roomPath = "static/product";

        System.out.println("Product path: " + name);

        File roomDir = new File(roomPath);

        if (!roomDir.exists()) {
            if (!roomDir.mkdirs()) {
                System.err.println("Failed to create directory: " + roomPath);
                return null;
            }
        }

        String path = roomPath + File.separator + name;
        File folder = new File(path);

        if (folder.exists()) {
            return path;
        }

        if (!folder.mkdirs()) {
            System.err.println("Failed to create directory: " + path);
            return null;
        }

        System.out.println("Directory created: " + path);

        return path;
    }

    public void deleteFolder(String folder) {
        String mypath = "static/product/" + File.separator + folder;

        File folderToDelete = new File(mypath);

        if (!folderToDelete.exists()) {
            System.err.println("Directory does not exist: " + mypath);
            return;
        }

        if (!deleteRecursive(folderToDelete)) {
            System.err.println("Failed to delete directory: " + mypath);
        }
    }

    private static boolean deleteRecursive(File file) {
        if (file.isDirectory()) {
            File[] contents = file.listFiles();

            if (contents != null) {

                for (File f : contents) {
                    if (!deleteRecursive(f)) {
                        return false;
                    }
                }
            }
        }

        return file.delete();
    }
}