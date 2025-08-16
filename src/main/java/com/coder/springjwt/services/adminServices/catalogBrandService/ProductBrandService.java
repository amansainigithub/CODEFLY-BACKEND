package com.coder.springjwt.services.adminServices.catalogBrandService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductBrandDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductBrandService {
    ResponseEntity<?> saveBrand(ProductBrandDto productBrandDto);

    ResponseEntity<?> deleteBrand(long brandId);

    ResponseEntity<?> getBrandById(long brandId);

    ResponseEntity<?> updateBrand(ProductBrandDto productBrandDto);

    ResponseEntity<?> getBrand(Integer page, Integer size);
}
