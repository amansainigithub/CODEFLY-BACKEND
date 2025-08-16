package com.coder.springjwt.services.adminServices.catalogNetQuantityService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductNetQuantityDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductNetQuantityService {
    ResponseEntity<?> saveNetQuantity(ProductNetQuantityDto productNetQuantityDto);

    ResponseEntity<?> deleteNetQuantity(long netQuantityId);

    ResponseEntity<?> getNetQuantityById(long netQuantityId);

    ResponseEntity<?> updateNetQuantity(ProductNetQuantityDto productNetQuantityDto);

    ResponseEntity<?> getNetQuantity(Integer page, Integer size);
}
