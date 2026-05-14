package com.example.wecommerce_api.repository.File;

import com.example.wecommerce_api.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,Long> {
FileEntity findByFileName(String filename);
}
