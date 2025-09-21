package com.coder.springjwt.services.sellerServices.sellerProductService;

import com.coder.springjwt.dtos.sellerPayloads.productDetailPayloads.ProductDetailsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface ProductService {
    ResponseEntity<?> saveProductDetails(ProductDetailsDto productDetailsDto, long variantId);
    ResponseEntity<?> saveProductFiles(MultipartFile[] files ,  MultipartFile video , long productId);
}
