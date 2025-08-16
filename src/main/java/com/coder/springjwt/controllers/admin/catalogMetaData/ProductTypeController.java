package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductTypeDto;
import com.coder.springjwt.services.adminServices.catalogTypeService.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_TYPE_CONTROLLER)
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @PostMapping(AdminUrlMappings.SAVE_TYPE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveType(@Validated @RequestBody ProductTypeDto productTypeDto) {
        return this.productTypeService.saveType(productTypeDto);
    }

    @GetMapping(AdminUrlMappings.DELETE_TYPE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteType(@PathVariable long typeId ) {
        return this.productTypeService.deleteType(typeId);
    }

    @GetMapping(AdminUrlMappings.GET_TYPE_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTypeById(@PathVariable long typeId ) {
        return this.productTypeService.getTypeById(typeId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_TYPE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateType(@Validated @RequestBody ProductTypeDto productTypeDto) {
        return this.productTypeService.updateType(productTypeDto);
    }


    @PostMapping(AdminUrlMappings.GET_TYPE)
    public ResponseEntity<?> getType(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productTypeService.getType(page,size);
    }


}
