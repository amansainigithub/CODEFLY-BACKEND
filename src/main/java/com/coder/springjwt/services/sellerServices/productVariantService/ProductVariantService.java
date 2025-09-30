package com.coder.springjwt.services.sellerServices.productVariantService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductVariantService {
    ResponseEntity<?> loadProductDetails(long productId);
}
