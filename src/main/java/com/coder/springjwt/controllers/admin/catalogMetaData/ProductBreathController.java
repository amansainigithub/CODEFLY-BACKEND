package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductBreathDto;
import com.coder.springjwt.services.adminServices.catalogBreathService.ProductBreathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_BREATH_CONTROLLER)
public class ProductBreathController {

    @Autowired
    private ProductBreathService productBreathService;

    @PostMapping(AdminUrlMappings.SAVE_BREATH)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveBreath(@Validated @RequestBody ProductBreathDto productBreathDto) {
        return this.productBreathService.saveBreath(productBreathDto);
    }

    @PostMapping(AdminUrlMappings.GET_BREATH)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getBreath(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productBreathService.getBreath(page,size);
    }
}
