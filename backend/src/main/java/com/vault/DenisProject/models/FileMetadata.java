package com.vault.DenisProject.models;



public class FileMetadata {

    private long id;
    private String fileName;
    private Long size;
    private String owner;


    public FileMetadata(long id, String fileName, Long size, String owner){
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
    public void setfileName(String fileName){
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
