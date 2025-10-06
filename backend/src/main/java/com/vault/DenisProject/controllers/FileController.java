package com.vault.DenisProject.controllers;

import com.vault.DenisProject.service.FileService;
import org.springframework.web.bind.annotation.*;
import com.vault.DenisProject.models.FileMetadata;


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

    @DeleteMapping
    public String deleteFile(@RequestBody long id){
        fileService.deleteFile(id);
        return "File " + id + " deleted successfully";
    }
}
