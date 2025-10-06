package com.vault.DenisProject.service;

import com.vault.DenisProject.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vault.DenisProject.models.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


//Creates a model for FileService
@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    public List<FileMetadata> getFiles() {

        return fileRepository.findAll();
    }

    public void addFile(String fileName) {
        FileMetadata ret = new FileMetadata(
            null,
            fileName,
            0L,
            "Denis Gusev"
        );
        fileRepository.save(ret);
    }

    public void deleteFile(long id) {
        if(fileRepository.existsById(id)){
            fileRepository.deleteById(id);
        }
        else{
           System.out.println("File with id " + id + " does not exist");
        }
    }
    public void updateFile(long id, String fileName, Long size) {
        FileMetadata file = fileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("File with ID " + id + " not found"));

        if (fileName != null && !fileName.isBlank()) {
            file.setFileName(fileName);
        }

        if (size != null) {
            file.setSize(size);
        }

        fileRepository.save(file);
    }

    public void saveFile(MultipartFile file, String owner) throws IOException {
        String fileName = file.getOriginalFilename();
        Long size = file.getSize();

        FileMetadata ret = new FileMetadata();
        ret.setFileName(fileName);
        ret.setSize(size);
        ret.setOwner(owner);

        fileRepository.save(ret);
    }
}
