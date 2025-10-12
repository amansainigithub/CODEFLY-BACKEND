package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductSizeVariantDto;
import com.coder.springjwt.services.adminServices.catalogSizeService.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_SIZE_CONTROLLER)
public class ProductSizeController {


    @Autowired
    private ProductSizeService productSizeService;

    @PostMapping(AdminUrlMappings.SAVE_SIZE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveSize(@Validated @RequestBody ProductSizeVariantDto productSizeVariantDto) {
        return this.productSizeService.saveSize(productSizeVariantDto);
    }


    @GetMapping(AdminUrlMappings.DELETE_SIZE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSize(@PathVariable long sizeId ) {
        return this.productSizeService.deleteSize(sizeId);
    }

    @GetMapping(AdminUrlMappings.GET_SIZE_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSizeById(@PathVariable long sizeId ) {
        return this.productSizeService.getSizeById(sizeId);
    }


    @PostMapping(AdminUrlMappings.UPDATE_SIZE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSize(@Validated @RequestBody ProductSizeVariantDto productSizeVariantDto) {
        return this.productSizeService.updateSize(productSizeVariantDto);
    }

    @PostMapping(AdminUrlMappings.GET_SIZE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSize(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productSizeService.getSize(page,size);
    }

}
