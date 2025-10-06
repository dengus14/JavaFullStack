package com.vault.DenisProject.service;

import com.vault.DenisProject.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vault.DenisProject.models.FileMetadata;
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
        fileRepository.deleteById(id);
    }
}
