package com.vault.DenisProject.controllers;

import com.vault.DenisProject.dto.FileUpdateRequest;
import com.vault.DenisProject.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vault.DenisProject.models.FileMetadata;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;



@RestController
@RequestMapping("/api/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {

        this.fileService = fileService;
    }

    @GetMapping
    public List<FileMetadata> getFiles(){

        return fileService.getFiles();
    }

    @PostMapping 
    public String addFile(@RequestBody String fileName){
        fileService.addFile(fileName);
        return "File " + fileName + " added successfully";

    }

    @DeleteMapping("/{id}")
    public String deleteFile(@PathVariable long id){
        fileService.deleteFile(id);
        return "File " + id + " deleted successfully";
    }

    @PutMapping("/{id}")
    public String updateFile(@PathVariable long id, @RequestBody FileUpdateRequest request){
        fileService.updateFile(id, request.getFileName(), request.getSize());
        return "File " + id + " updated successfully";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("owner") String owner) throws IOException {
        fileService.saveFile(file,owner);
        return "File " + file.getOriginalFilename() + " uploaded successfully";

    }
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        InputStream stream = fileService.downloadFile(id);
        InputStreamResource resource = new InputStreamResource(stream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fileName")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body((Resource) resource);
    }

}
