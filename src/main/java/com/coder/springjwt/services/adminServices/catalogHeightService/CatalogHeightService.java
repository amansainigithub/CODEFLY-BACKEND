package com.coder.springjwt.services.adminServices.catalogHeightService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductHeightDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface CatalogHeightService {
    ResponseEntity<?> saveHeight(ProductHeightDto saveCatalogHeight);

    ResponseEntity<?> getHeight(Integer page, Integer size);
}
