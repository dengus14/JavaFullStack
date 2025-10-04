package com.vault.DenisProject.controller;

import com.vault.DenisProject.service.FileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public List<String> getFiles(){
        return fileService.getFiles();
    }

    @PostMapping 
    public String addFile(@RequestParam String file){
        fileService.addFile(file);
        return "File " + file + " added successfully";

    }

    @DeleteMapping
    public String deleteFile(@RequestParam String file){
        fileService.deleteFile(file);
        return "File " + file + " deleted successfully";
    }
}
