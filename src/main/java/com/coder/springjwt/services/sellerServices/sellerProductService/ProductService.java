package com.coder.springjwt.services.sellerServices.sellerProductService;

import com.coder.springjwt.dtos.sellerPayloads.productDetailPayloads.ProductDetailsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductService {
    ResponseEntity<?> saveProductDetails(ProductDetailsDto productDetailsDto, long variantId);
}
