package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductWeightDto;
import com.coder.springjwt.services.adminServices.catalogWeightService.ProductWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_WEIGHT_CONTROLLER)
public class ProductWeightController {

    @Autowired
    private ProductWeightService productWeightService;

    @PostMapping(AdminUrlMappings.SAVE_WEIGHT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveWeight(@Validated @RequestBody ProductWeightDto productWeightDto) {
        return this.productWeightService.saveWeight(productWeightDto);
    }

    @PostMapping(AdminUrlMappings.GET_WEIGHT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getWeight(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productWeightService.getWeight(page,size);
    }
}
