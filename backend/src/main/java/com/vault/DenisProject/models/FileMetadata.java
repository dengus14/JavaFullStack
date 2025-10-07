package com.vault.DenisProject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "files")
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;      // S3 key
    private String originalName;  // human-readable
    private Long size;            // size of the file
    private String owner;         //owner of the file
    private String contentType;   //which type of file this is

    @Column(name = "s3_url")
    private String s3Url;

    // Default constructor (JPA requires this)
    public FileMetadata() {}

    // Custom constructor
    public FileMetadata(Long id, String fileName, Long size, String owner) {
        this.id = id;
        this.fileName = fileName;
        this.size = size;
        this.owner = owner;
    }



    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getS3Url() {
        return s3Url;
    }

    public void setS3Url(String s3Url) {
        this.s3Url = s3Url;
    }
}
