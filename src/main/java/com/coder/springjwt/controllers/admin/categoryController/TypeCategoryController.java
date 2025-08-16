package com.coder.springjwt.controllers.admin.categoryController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.TypeCategoryDto;
import com.coder.springjwt.services.adminServices.categories.TypeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(AdminUrlMappings.TYPE_CATEGORY_CONTROLLER)
public class TypeCategoryController {

    @Autowired
    private TypeCategoryService typeCategoryService;

    @PostMapping(AdminUrlMappings.SAVE_TYPE_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveTypeCategory(@Validated @RequestBody TypeCategoryDto typeCategoryDto) {
        return this.typeCategoryService.saveTypeCategory(typeCategoryDto);
    }

    @GetMapping(AdminUrlMappings.GET_TYPE_CATEGORY_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTypeCategoryList() {
        return this.typeCategoryService.getTypeCategoryList();
    }


    @GetMapping(AdminUrlMappings.DELETE_TYPE_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTypeCategoryById(@PathVariable long categoryId ) {
        return this.typeCategoryService.deleteTypeCategoryById(categoryId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_TYPE_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTypeCategory(@Validated @RequestBody TypeCategoryDto typeCategoryDto ) {
        return this.typeCategoryService.updateTypeCategory(typeCategoryDto);
    }

    @GetMapping(AdminUrlMappings.GET_TYPE_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTypeCategoryById(@PathVariable long categoryId ) {
        return this.typeCategoryService.getTypeCategoryById(categoryId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_TYPE_CATEGORY_FILE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTypeCategoryFile(@RequestParam(value = "file") MultipartFile file , @PathVariable Long typeCategoryId)
    {
        return typeCategoryService.updateTypeCategoryFile(file,typeCategoryId);
    }

}
