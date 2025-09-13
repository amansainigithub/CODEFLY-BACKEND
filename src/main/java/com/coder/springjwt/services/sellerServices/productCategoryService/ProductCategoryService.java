package com.coder.springjwt.services.sellerServices.productCategoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductCategoryService {

    ResponseEntity<?> getRootCategory();

    ResponseEntity<?> getSubCategory(long id);

    ResponseEntity<?> getTypeCategory(long id);

    ResponseEntity<?> getVariantCategory(long id);
}
