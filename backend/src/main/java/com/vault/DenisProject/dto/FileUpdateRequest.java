package com.vault.DenisProject.dto;


public class FileUpdateRequest {
    String fileName;
    Long size;


    public FileUpdateRequest() {

    }

    FileUpdateRequest(String fileName, Long size) {
        this.fileName = fileName;
        this.size = size;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public Long getSize() {
        return size;
    }
}
