package com.coder.springjwt.services.adminServices.catalogTypeService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductTypeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductTypeService {
    ResponseEntity<?> saveType(ProductTypeDto productTypeDto);

    ResponseEntity<?> deleteType(long typeId);

    ResponseEntity<?> getTypeById(long typeId);

    ResponseEntity<?> updateType(ProductTypeDto productTypeDto);

    ResponseEntity<?> getType(Integer page, Integer size);
}
