package com.bessy.filestorage.service;

import com.bessy.filestorage.exc.GenericErrorResponse;
import com.bessy.filestorage.model.File;
import com.bessy.filestorage.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final FileRepository fileRepository;
    private String FOLDER_PATH;

    @PostConstruct
    public void init() {
        String currentWorkingDirectory = System.getProperty("user.dir");

        FOLDER_PATH = currentWorkingDirectory + "/file-storage/src/main/resources/attachments";

        java.io.File targetFolder = new java.io.File(FOLDER_PATH);

        if (!targetFolder.exists()) {
            boolean directoriesCreated = targetFolder.mkdirs();
            if (!directoriesCreated) {
                throw GenericErrorResponse.builder()
                        .message("Unable to create directories")
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build();
            }
        }
    }

    public String uploadImageToFileSystem(MultipartFile file) {
        File persistentFile = fileRepository.save(File.builder()
                .type(file.getContentType())
                .build());

        UUID uuid = persistentFile.getId();
        String filePath = FOLDER_PATH + "/" + uuid.toString();
        try {
            file.transferTo(new java.io.File(filePath));
        } catch (IOException e) {
            throw GenericErrorResponse.builder()
                    .message("Unable to save file to storage")
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

        persistentFile.setFilePath(filePath);
        fileRepository.save(persistentFile);

        return uuid.toString();
    }

    public byte[] downloadImageFromFileSystem(String id) {
        try {
            return Files.readAllBytes(new java.io.File(findFileById(id)
                    .getFilePath()).toPath());
        } catch (IOException e) {
            throw GenericErrorResponse.builder()
                    .message("Unable to read file from storage")
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    public void deleteImageFromFileSystem(String id) {
        java.io.File file = new java.io.File(findFileById(id).getFilePath());

        boolean deletionResult = file.delete();

        if (deletionResult) fileRepository.deleteById(id);

        else throw GenericErrorResponse.builder()
                .message("Unable to delete file from storage")
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }


    protected File findFileById(String id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> GenericErrorResponse.builder()
                        .message("File not found")
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build());
    }
}