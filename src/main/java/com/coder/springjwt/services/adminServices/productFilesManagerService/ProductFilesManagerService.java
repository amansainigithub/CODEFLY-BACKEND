package com.coder.springjwt.services.adminServices.productFilesManagerService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface ProductFilesManagerService {
    ResponseEntity<?> getProductFilesById(long productId);

    ResponseEntity<?> modifiedProductFiles(MultipartFile files, String productFileId , String productId);
}
