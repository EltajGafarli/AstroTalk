package com.example.astrotalk.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileService {
    @Value("${file.storage.path}")
    private static String rootLocationStr;

    public static String handleFileUpload(MultipartFile file, String folder) {

        Path rootLocation = Paths.get(rootLocationStr);

        if(file.isEmpty()) {
            throw new RuntimeException("Cannot upload an empty file.");
        }

        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path destinationFile = rootLocation.resolve(filename)
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                throw new SecurityException("Cannot store file outside current directory.");
            }

            file.transferTo(destinationFile);

            return filename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file due to: " + e.getMessage(), e);
        }
    }
}
