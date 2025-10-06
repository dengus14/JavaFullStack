package com.vault.DenisProject.repository;

import com.vault.DenisProject.models.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileRepository extends JpaRepository<FileMetadata, Long> {
}
