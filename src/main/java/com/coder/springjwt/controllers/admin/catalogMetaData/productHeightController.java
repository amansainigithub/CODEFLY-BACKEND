package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductHeightDto;
import com.coder.springjwt.services.adminServices.catalogHeightService.CatalogHeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_HEIGHT_CONTROLLER)
public class productHeightController {

    @Autowired
    private CatalogHeightService catalogHeightService;

    @PostMapping(AdminUrlMappings.SAVE_HEIGHT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveHeight(@Validated @RequestBody ProductHeightDto productHeightDto) {
        return this.catalogHeightService.saveHeight(productHeightDto);
    }

    @PostMapping(AdminUrlMappings.GET_HEIGHT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getHeight(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.catalogHeightService.getHeight(page,size);
    }
}
