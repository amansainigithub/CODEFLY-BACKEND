package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductMaterialDto;
import com.coder.springjwt.services.adminServices.catalogMaterialService.ProductMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_MATERIAL_CONTROLLER)
public class ProductMaterialController {

    @Autowired
    private ProductMaterialService productMaterialService;

    @PostMapping(AdminUrlMappings.SAVE_MATERIAL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveMaterial(@Validated @RequestBody ProductMaterialDto productMaterialDto) {
        return this.productMaterialService.saveMaterial(productMaterialDto);
    }


    @GetMapping(AdminUrlMappings.DELETE_MATERIAL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMaterial(@PathVariable long materialId ) {
        return this.productMaterialService.deleteCatalogMaterial(materialId);
    }

    @GetMapping(AdminUrlMappings.GET_MATERIAL_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getMaterialById(@PathVariable long materialId ) {
        return this.productMaterialService.getCatalogMaterialById(materialId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_MATERIAL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateMaterial(@Validated @RequestBody ProductMaterialDto productMaterialDto) {
        return this.productMaterialService.updateCatalogMaterial(productMaterialDto);
    }

    @PostMapping(AdminUrlMappings.GET_MATERIAL)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getMaterial(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productMaterialService.getCatalogMaterial(page,size);
    }
}
