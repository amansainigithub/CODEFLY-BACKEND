package com.coder.springjwt.controllers.admin.categoryController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.SubCategoryDto;
import com.coder.springjwt.services.adminServices.categories.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(AdminUrlMappings.SUB_CATEGORY_CONTROLLER)
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @PostMapping(AdminUrlMappings.SAVE_SUB_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveSubCategory(@Validated @RequestBody SubCategoryDto subCategoryDto) {
        return this.subCategoryService.saveSubCategory(subCategoryDto);
    }

    @GetMapping(AdminUrlMappings.GET_SUB_CATEGORY_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSubCategoryList() {
        return this.subCategoryService.getSubCategoryList();
    }


    @GetMapping(AdminUrlMappings.DELETE_SUB_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSubCategoryById(@PathVariable long categoryId ) {
        return this.subCategoryService.deleteSubCategoryById(categoryId);
    }

    @GetMapping(AdminUrlMappings.GET_SUB_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSubCategoryById(@PathVariable long categoryId ) {
        return this.subCategoryService.getSubCategoryById(categoryId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_SUB_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSubCategory(@Validated @RequestBody SubCategoryDto subCategoryDto ) {
        return this.subCategoryService.updateSubCategory(subCategoryDto);
    }

    @PostMapping(AdminUrlMappings.UPDATE_SUB_CATEGORY_FILE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSubCategoryFile(@RequestParam(value = "file") MultipartFile file , @PathVariable Long subCategoryId)
    {
        return subCategoryService.updateSubCategoryFile(file,subCategoryId);
    }
}
