package com.coder.springjwt.services.adminServices.productManagerService;

import com.coder.springjwt.dtos.sellerPayloads.productDetailPayloads.ProductDetailsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductManageService {

    ResponseEntity<?> retrieveProductDetails(long productId);

    ResponseEntity<?> updateProductDetails(ProductDetailsDto productDetailsDto, long productId);
}
