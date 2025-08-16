package com.coder.springjwt.controllers.admin.categoryController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.VariantCategoryDto;
import com.coder.springjwt.services.adminServices.categories.VariantCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(AdminUrlMappings.VARIANT_CATEGORY_CONTROLLER)
public class VariantCategoryController {

    @Autowired
    private VariantCategoryService variantCategoryService;

    @PostMapping(AdminUrlMappings.SAVE_VARIANT_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveVariantCategory(@Validated @RequestBody VariantCategoryDto variantCategoryDto) {
        return this.variantCategoryService.saveVariantCategory(variantCategoryDto);
    }

    @GetMapping(AdminUrlMappings.GET_VARIANT_CATEGORY_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getVariantCategoryList() {
        return this.variantCategoryService.getVariantCategoryList();
    }


    @GetMapping(AdminUrlMappings.DELETE_VARIANT_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteVariantCategoryById(@PathVariable long categoryId ) {
        return this.variantCategoryService.deleteVariantCategoryById(categoryId);
    }


    @PostMapping(AdminUrlMappings.UPDATE_VARIANT_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateVariantCategory(@Validated  @RequestBody VariantCategoryDto variantCategoryDto ) {
        return this.variantCategoryService.updateVariantCategory(variantCategoryDto);
    }


    @GetMapping(AdminUrlMappings.GET_VARIANT_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getVariantCategoryById(@PathVariable long categoryId ) {
        return this.variantCategoryService.getVariantCategoryById(categoryId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_VARIANT_CATEGORY_FILE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateVariantCategoryFile(@RequestParam(value = "file") MultipartFile file ,
                                                    @PathVariable Long variantCategoryId)
    {
        return variantCategoryService.updateVariantCategoryFile(file,variantCategoryId);
    }



}
