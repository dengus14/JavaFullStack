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
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import java.time.Duration;



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
    private final S3Presigner presigner;

    private final S3Client s3Client;
    @Value("${aws.s3.bucket}")
    private String bucketName;

    public FileService(FileRepository fileRepository,  S3Client s3Client, S3Presigner presigner) {
        this.fileRepository = fileRepository;
        this.s3Client = s3Client;
        this.presigner = presigner;
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

    public FileMetadata getFileMetadata(Long id) {
        if(fileRepository.findById(id).isPresent()) {
            return fileRepository.findById(id).get();
        }
        return null;
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
        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );

        String fileUrl = "https://" + bucketName + ".s3.amazonaws.com/" + key;

        FileMetadata meta = new FileMetadata();
        meta.setFileName(key);
        meta.setOriginalName(file.getOriginalFilename());
        meta.setOwner(owner);
        meta.setSize(file.getSize());
        meta.setContentType(file.getContentType());
        meta.setS3Url(fileUrl);

        fileRepository.save(meta);
    }


    public InputStream downloadFile(Long id) {

        FileMetadata ret =  fileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("File with ID " + id + " not found"));

        GetObjectRequest newRequest = GetObjectRequest.builder().bucket(bucketName).key(ret.getFileName()).build();

        try {
            return s3Client.getObject(newRequest);
        } catch (S3Exception e) {
            throw new RuntimeException("Error downloading file from S3: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    public String generateDownloadUrl(Long id) {
        FileMetadata meta = fileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("File not found"));

        String safeFileName = meta.getOriginalName()
                .replaceAll("[^a-zA-Z0-9._-]", "_"); // removes special characters safely

        GetObjectRequest getReq = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(meta.getFileName())
                .responseContentDisposition("attachment; filename=\"" + safeFileName + "\"")
                .responseContentType(meta.getContentType())
                .build();

        GetObjectPresignRequest presignReq = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(5))
                .getObjectRequest(getReq)
                .build();

        return presigner.presignGetObject(presignReq).url().toString();
    }


}
