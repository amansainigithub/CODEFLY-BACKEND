package com.coder.springjwt.services.sellerServices.productVariantService;

import com.coder.springjwt.dtos.sellerPayloads.productDetailPayloads.ProductDetailsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface ProductVariantService {
    ResponseEntity<?> loadProductDetails(long productId);

    ResponseEntity<?> saveProductVariantDetails(ProductDetailsDto productDetailsDto, long variantId , long existingProductId);

    ResponseEntity<?> saveProductVariantFiles(MultipartFile[] files, MultipartFile video, long productId , long existingProductId);
}
