package com.vault.DenisProject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "files")
public class FileMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private Long size;
    private String owner;

    public FileMetadata() {

    }

    public FileMetadata(Long id, String fileName, Long size, String owner){
        this.id = id;
        this.fileName = fileName;
        this.size = size;
        this.owner = owner;
    }



    public long getId(){
        return this.id;
    }
    public void setId(long id){
        this.id = id;
    }
    public String getFileName(){
        return this.fileName;
    }
    public void setFileName(String fileName){
        this.fileName = fileName;
    }
    public Long getSize(){
        return this.size;
    }
    public void setSize(Long size){
        this.size = size;
    }
    public String getOwner(){
        return this.owner;
    }
    public void setOwner(String owner){
        this.owner = owner;
    }

    
}
