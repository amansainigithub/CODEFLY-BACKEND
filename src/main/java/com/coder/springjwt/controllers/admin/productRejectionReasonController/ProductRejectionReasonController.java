package com.coder.springjwt.controllers.admin.productRejectionReasonController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.productRejectionReasonDto.ProductRejectionReasonDto;
import com.coder.springjwt.services.adminServices.ProductRejectionReasonService.ProductRejectionReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_REJECTION_REASON_CONTROLLER)
public class ProductRejectionReasonController {

    @Autowired
    private ProductRejectionReasonService productRejectionReasonService;

    @PostMapping(AdminUrlMappings.CREATE_REJECTION_REASON)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createRejectionReason(@Validated @RequestBody ProductRejectionReasonDto productRejectionReasonDto) {
        return this.productRejectionReasonService.createRejectionReason(productRejectionReasonDto);
    }

    @GetMapping(AdminUrlMappings.GET_REJECTION_REASONS)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRejectionReasons() {
        return this.productRejectionReasonService.getRejectionReasons();
    }

    @GetMapping(AdminUrlMappings.DELETE_REJECTION_REASON)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRejectionReason(@PathVariable long rejectionId ) {
        return this.productRejectionReasonService.deleteRejectionReason(rejectionId);
    }

    @GetMapping(AdminUrlMappings.GET_REJECTION_REASON_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRejectionReasonById(@PathVariable long rejectionId ) {
        return this.productRejectionReasonService.getRejectionReasonById(rejectionId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_REJECTION_REASON)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRejectionReason(@Validated  @RequestBody ProductRejectionReasonDto productRejectionReasonDto ) {
        return this.productRejectionReasonService.updateRejectionReason(productRejectionReasonDto);
    }


    @GetMapping(AdminUrlMappings.FIND_BY_ROOT_REJECTION_CATEGORY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findByRootRejectionCategory(@PathVariable long rejectionId) {
        return this.productRejectionReasonService.findByRootRejectionCategory(rejectionId);
    }

}
