package com.coder.springjwt.services.adminServices.catalogBreathService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductBreathDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductBreathService {
    ResponseEntity<?> saveBreath(ProductBreathDto productBreathDto);

    ResponseEntity<?> getBreath(Integer page, Integer size);
}
