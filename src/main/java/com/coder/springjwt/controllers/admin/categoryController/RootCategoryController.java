package com.coder.springjwt.controllers.admin.categoryController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.RootCategoryDto;
import com.coder.springjwt.services.adminServices.categories.RootCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(AdminUrlMappings.ROOT_CATEGORY_CONTROLLER)
public class RootCategoryController {

    @Autowired
    private RootCategoryService rootCategoryService;

    @PostMapping(AdminUrlMappings.CREATE_ROOT_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createRootCategory(@Validated @RequestBody RootCategoryDto rootCategoryDto) {
        return this.rootCategoryService.saveRootCategory(rootCategoryDto);
    }

    @GetMapping(AdminUrlMappings.GET_ROOT_CATEGORY_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRootCategoryList() {
        return this.rootCategoryService.getRootCategoryList();
    }

    @GetMapping(AdminUrlMappings.DELETE_ROOT_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRootCategoryById(@PathVariable long categoryId ) {
        return this.rootCategoryService.deleteRootCategoryById(categoryId);
    }


    @GetMapping(AdminUrlMappings.GET_ROOT_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRootCategoryById(@PathVariable long categoryId ) {
        return this.rootCategoryService.getRootCategoryById(categoryId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_ROOT_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRootCategory(@Validated @RequestBody RootCategoryDto rootCategoryDto ) {
        return this.rootCategoryService.updateRootCategory(rootCategoryDto);
    }

    @PostMapping(AdminUrlMappings.UPDATE_ROOT_CATEGORY_FILE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRootCategoryFile(@RequestParam(value = "file") MultipartFile file , @PathVariable Long rootCategoryId)
    {
        return this.rootCategoryService.updateRootCategoryFile(file,rootCategoryId);
    }
}
