package com.bessy.filestorage.service;

import com.bessy.filestorage.enums.EntityFolder;
import com.bessy.filestorage.exc.GenericErrorResponse;
import com.bessy.filestorage.model.File;
import com.bessy.filestorage.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;

@Slf4j
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
        return persistFile(null, null, file);
    }

    public String uploadImageToSpecificFolder(String folderId,  String resourceId, MultipartFile file) {
        return persistFile(folderId, resourceId, file);
    }

    @Transactional
    private String persistFile(String folderId,  String resourceId, MultipartFile file) {
        UUID uuid = Objects.nonNull(resourceId) ? UUID.fromString(resourceId) : UUID.randomUUID();
        File persistentFile = fileRepository.save(File.builder()
                .id(uuid)
                .type(file.getContentType())
                .build());
        persistentFile.setFilePath(saveFile(folderId, uuid, file));
        fileRepository.save(persistentFile);
        return uuid.toString();
    }

    private String buildFilePath(String folderId, UUID uuid) {
        return FOLDER_PATH + (Objects.nonNull(folderId) ? ("/" + folderId) : "") + "/" + uuid;
    }


    private String saveFile(String folderId, UUID uuid, MultipartFile file) {
        try {
            // Define the base directory for uploads (outside of src/main/resources)
            Path baseDirectoryPath = Paths.get(FOLDER_PATH);

            // Create a subdirectory if folderId is provided
            Path directoryPath = Objects.nonNull(folderId)
                    ? baseDirectoryPath.resolve(folderId)
                    : baseDirectoryPath;

            // Create directories if they don't exist
            if (Files.notExists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // Extract the original file extension from MultipartFile
            String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
            String fileExtension = getFileExtension(originalFilename);

            // Define the full file path with the UUID and the correct extension
            Path filePath = directoryPath.resolve(uuid.toString() + fileExtension);

            // Delete any existing file with the same UUID (with any extension)
            deleteExistingFileWithUUID(directoryPath, uuid);

            // Transfer the file to the destination
            file.transferTo(filePath.toFile());

            return filePath.toString();

        } catch (IOException e) {
            log.error("saveFile error {}", e.getMessage());
            throw GenericErrorResponse.builder()
                    .message("Unable to save file to storage")
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // Helper method to get the file extension
    private String getFileExtension(String filename) {
        int lastIndexOfDot = filename.lastIndexOf(".");
        if (lastIndexOfDot == -1) {
            return ""; // No extension found
        }
        return filename.substring(lastIndexOfDot); // Returns the extension with the dot (e.g., ".png", ".jpg")
    }

    // Method to delete an existing file with the same UUID in the directory
    private void deleteExistingFileWithUUID(Path directoryPath, UUID uuid) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath, uuid.toString() + "*")) {
            for (Path entry : stream) {
                Files.deleteIfExists(entry);
                log.info("Deleted existing file: {}", entry.getFileName());
            }
        } catch (IOException e) {
            log.error("Error deleting existing file: {}", e.getMessage());
            throw e;
        }
    }

    // Method to fetch and download the file by UUID
    public void fetchFile(String folderId, UUID uuid, HttpServletResponse response) {
        try {
            // Define the base directory for uploads (outside of src/main/resources)
            Path baseDirectoryPath = Paths.get(FOLDER_PATH);

            // Create a subdirectory if folderId is provided
            Path directoryPath = Objects.nonNull(folderId)
                    ? baseDirectoryPath.resolve(folderId)
                    : baseDirectoryPath;

            // Find the file with the same UUID (ignoring extension)
            Path filePath = findFileByUUID(directoryPath, uuid);

            if (filePath == null) {
                log.error("File not found for UUID: {}", uuid);
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.getWriter().write("File not found");
                return;
            }

            // Set the content type and file download headers
            response.setContentType(Files.probeContentType(filePath));  // Automatically detect content type
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filePath.getFileName() + "\"");

            // Write the file to the HTTP response output stream
            Files.copy(filePath, response.getOutputStream());
            response.flushBuffer();

        } catch (IOException e) {
            log.error("fetchFile error: {}", e.getMessage());
            try {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.getWriter().write("Unable to fetch file");
            } catch (IOException ioException) {
                log.error("Error writing error response: {}", ioException.getMessage());
            }
        }
    }

    // Helper method to find the file by UUID in the directory (ignores file extension)
    private Path findFileByUUID(Path directoryPath, UUID uuid) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath, uuid.toString() + "*")) {
            for (Path entry : stream) {
                log.info("Found file: {}", entry.getFileName());
                return entry; // Return the first matching file (UUID matches)
            }
        } catch (IOException e) {
            log.error("Error finding file by UUID: {}", e.getMessage());
            throw e;
        }
        return null; // No file found with the given UUID
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

    // Method to check if the folderId is valid based on the EntityFolder enum
    public boolean isValidEntityFolder(String folderId) {
        try {
            EntityFolder.valueOf(folderId.toUpperCase()); // Check if folderId is a valid enum constant
            return true;
        } catch (IllegalArgumentException e) {
            return false; // folderId is not in the enum
        }
    }
}