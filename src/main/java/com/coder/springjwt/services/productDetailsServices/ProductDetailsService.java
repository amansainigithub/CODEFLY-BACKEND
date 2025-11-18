package com.coder.springjwt.services.productDetailsServices;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductDetailsService {

    ResponseEntity<?> getProductDetails(Long productId ,String productName);
}
