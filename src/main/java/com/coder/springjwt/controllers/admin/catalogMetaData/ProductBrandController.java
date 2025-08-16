package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductBrandDto;
import com.coder.springjwt.services.adminServices.catalogBrandService.ProductBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_BRAND_CONTROLLER)
public class ProductBrandController {

    @Autowired
    private ProductBrandService productBrandService;

    @PostMapping(AdminUrlMappings.SAVE_BRAND)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveBrand(@Validated @RequestBody ProductBrandDto productBrandDto) {
        return this.productBrandService.saveBrand(productBrandDto);
    }

    @GetMapping(AdminUrlMappings.DELETE_BRAND)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBrand(@PathVariable long brandId ) {
        return this.productBrandService.deleteBrand(brandId);
    }

    @GetMapping(AdminUrlMappings.GET_BRAND_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getBrandById(@PathVariable long brandId ) {
        return this.productBrandService.getBrandById(brandId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_BRAND)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBrand(@Validated @RequestBody ProductBrandDto productBrandDto) {
        return this.productBrandService.updateBrand(productBrandDto);
    }

    @PostMapping(AdminUrlMappings.GET_BRAND)
    public ResponseEntity<?> getBrand(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productBrandService.getBrand(page,size);
    }


}
