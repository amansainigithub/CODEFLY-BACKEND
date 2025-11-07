package com.coder.springjwt.controllers.admin.productRejectionReasonController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.productRejectionReasonDto.RootRejectionCategoryDto;
import com.coder.springjwt.services.adminServices.ProductRejectionReasonService.RootRejectionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.ROOT_REJECTION_CATEGORY_CONTROLLER)
public class RootRejectionCategoryController {

    @Autowired
    private RootRejectionCategoryService createRootRejectionCategory;

    @PostMapping(AdminUrlMappings.CREATE_ROOT_REJECTION_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createRootRejectionCategory(@Validated @RequestBody RootRejectionCategoryDto rootRejectionCategoryDto) {
        return this.createRootRejectionCategory.createRootRejectionCategory(rootRejectionCategoryDto);
    }

    @GetMapping(AdminUrlMappings.GET_ROOT_REJECTION_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRootRejectionCategory() {
        return this.createRootRejectionCategory.getRootRejectionCategory();
    }

    @GetMapping(AdminUrlMappings.DELETE_ROOT_REJECTION_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRootRejectionCategory(@PathVariable long rejectionId ) {
        return this.createRootRejectionCategory.deleteRootRejectionCategory(rejectionId);
    }

    @GetMapping(AdminUrlMappings.GET_ROOT_REJECTION_CATEGORY_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRootRejectionCategoryById(@PathVariable long rejectionId ) {
        return this.createRootRejectionCategory.getRootRejectionCategoryById(rejectionId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_ROOT_REJECTION_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRootRejectionCategory(@Validated  @RequestBody RootRejectionCategoryDto rootRejectionCategoryDto ) {
        return this.createRootRejectionCategory.updateRootRejectionCategory(rootRejectionCategoryDto);
    }


}
