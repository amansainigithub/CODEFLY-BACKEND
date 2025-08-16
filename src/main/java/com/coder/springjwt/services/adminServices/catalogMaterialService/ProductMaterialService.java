package com.coder.springjwt.services.adminServices.catalogMaterialService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductMaterialDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductMaterialService {
    ResponseEntity<?> saveMaterial(ProductMaterialDto productMaterialDto);

    ResponseEntity<?> deleteCatalogMaterial(long materialId);

    ResponseEntity<?> getCatalogMaterialById(long materialId);

    ResponseEntity<?> updateCatalogMaterial(ProductMaterialDto productMaterialDto);

    ResponseEntity<?> getCatalogMaterial(Integer page, Integer size);
}
