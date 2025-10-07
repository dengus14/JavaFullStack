package com.vault.DenisProject.service;

import com.vault.DenisProject.repository.FileRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.vault.DenisProject.models.FileMetadata;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;


//Creates a model for FileService
@Service
public class FileService {

    private final FileRepository fileRepository;

    private final S3Client s3Client;
    @Value("${aws.s3.bucket}")
    private String bucketName;

    public FileService(FileRepository fileRepository,  S3Client s3Client) {
        this.fileRepository = fileRepository;
        this.s3Client = s3Client;
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

        String key = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();


        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );


        String fileUrl = String.format("https://%s.s3.amazonaws.com/%s", bucketName, key);


        FileMetadata metadata = new FileMetadata();
        metadata.setFileName(file.getOriginalFilename());
        metadata.setOwner(owner);
        metadata.setSize(file.getSize());
        metadata.setS3Url(fileUrl);

        fileRepository.save(metadata);
    }

    public InputStream downloadFile(Long id) {

        FileMetadata ret =  fileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("File with ID " + id + " not found"));

        GetObjectRequest newRequest = GetObjectRequest.builder().bucket(bucketName).key(ret.getS3Url()).build();

        try {
            return s3Client.getObject(newRequest);
        } catch (S3Exception e) {
            throw new RuntimeException("Error downloading file from S3: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

}
