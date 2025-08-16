package com.coder.springjwt.services.adminServices.catalogSizeService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductSizeVariantDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductSizeService {

    ResponseEntity<?> saveSize(ProductSizeVariantDto productSizeVariantDto);

    ResponseEntity<?> deleteSize(long sizeId);

    ResponseEntity<?> getSizeById(long sizeId);

    ResponseEntity<?> updateSize(ProductSizeVariantDto productSizeVariantDto);

    ResponseEntity<?> getSize(Integer page, Integer size);
}
