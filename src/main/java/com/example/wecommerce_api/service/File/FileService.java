package com.example.wecommerce_api.service.File;

import com.example.wecommerce_api.entity.FileEntity;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FileService {
    void InsertFile(FileEntity fileEntity);
    String Uplaodfile(MultipartFile file) throws IOException;
    Resource getFile(String fileName) throws IOException;
}
