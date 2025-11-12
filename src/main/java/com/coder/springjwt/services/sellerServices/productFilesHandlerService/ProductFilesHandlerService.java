package com.coder.springjwt.services.sellerServices.productFilesHandlerService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface ProductFilesHandlerService {
    ResponseEntity<?> getProductFilesByIdSeller(long productId, String username);

    ResponseEntity<?> modifiedProductFilesBySeller(MultipartFile files, String fileId, String productId, String username);

    ResponseEntity<?>  uploadNewFileBySeller(MultipartFile files, String productId, String username);
}
