package com.coder.springjwt.services.adminServices.catalogLengthService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductLengthDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductLengthService {
    ResponseEntity<?> saveLength(ProductLengthDto productLengthDto);

    ResponseEntity<?> getLength(Integer page, Integer size);
}
