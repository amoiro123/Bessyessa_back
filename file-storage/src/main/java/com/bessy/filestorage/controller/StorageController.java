package com.bessy.filestorage.controller;

import com.bessy.filestorage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/v1/file-storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageService storageService;
    private final String INVALID_FOLDER = "Invalid folderId. Accepted values: brands, models, and users.";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImageToFIleSystem(@RequestPart("image") MultipartFile file) {
        return ResponseEntity.ok().body(storageService.uploadImageToFileSystem(file));
    }

    @PostMapping("/upload/{folderId}/{resourceId}")
    public ResponseEntity<String> uploadImageToSpecificFolder(@PathVariable("folderId") String folderId, @PathVariable("resourceId") String resourceId, @RequestPart("image") MultipartFile file) {
        if (!storageService.isValidEntityFolder(folderId))
            return ResponseEntity.badRequest().body(INVALID_FOLDER);
        return ResponseEntity.ok().body(storageService.uploadImageToSpecificFolder(folderId, resourceId, file));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String id) {
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/png"))
                .body(storageService.downloadImageFromFileSystem(id));
    }


    @GetMapping("/download/{folderId}/{uuid}")
    public void downloadFile(
            @PathVariable String folderId,
            @PathVariable UUID uuid,
            HttpServletResponse response) throws IOException {
        if (!storageService.isValidEntityFolder(folderId)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(INVALID_FOLDER);
            return;
        }
        storageService.fetchFile(folderId, uuid, response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteImageFromFileSystem(@PathVariable String id) {
        storageService.deleteImageFromFileSystem(id);
        return ResponseEntity.ok().build();
    }
}