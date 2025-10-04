package com.vault.DenisProject.service;

import org.springframework.stereotype.Service;
import com.vault.DenisProject.models.FileMetadata;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


//Creates a model for FileService
@Service
public class FileService {
    private List<FileMetadata> files = new ArrayList<>();
    private final AtomicLong longVal = new AtomicLong(0);

    public List<FileMetadata> getFiles() {
        return files;
    }

    public void addFile(String fileName) {
        FileMetadata ret = new FileMetadata(
            longVal.incrementAndGet(),
            fileName,
            0L,
            "Denis Gusev"
        );
        files.add(ret);
    }

    public void deleteFile(String fileName) {
        files.removeIf(file -> file.getFileName().equals(fileName));
    }
}
