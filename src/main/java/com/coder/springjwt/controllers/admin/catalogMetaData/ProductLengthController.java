package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductLengthDto;
import com.coder.springjwt.services.adminServices.catalogLengthService.ProductLengthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_LENGTH_CONTROLLER)
public class ProductLengthController {

    @Autowired
    private ProductLengthService productLengthService;

    @PostMapping(AdminUrlMappings.SAVE_LENGTH)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveLength(@Validated @RequestBody ProductLengthDto productLengthDto) {
        return this.productLengthService.saveLength(productLengthDto);
    }

    @PostMapping(AdminUrlMappings.GET_LENGTH)
    public ResponseEntity<?> getLength(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productLengthService.getLength(page,size);
    }
}
