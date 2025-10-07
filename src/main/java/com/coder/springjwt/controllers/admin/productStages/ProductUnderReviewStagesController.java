package com.coder.springjwt.controllers.admin.productStages;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.services.adminServices.productStageServices.productUnderReviewStageService.ProductUnderReviewStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_UNDER_REVIEW_STAGE_CONTROLLER)
public class ProductUnderReviewStagesController {

    @Autowired
    private ProductUnderReviewStageService productUnderReviewStageService;

    @GetMapping(AdminUrlMappings.PRODUCT_UNDER_REVIEW_STAGE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> productUnderReviewStage() {
        return this.productUnderReviewStageService.productUnderReviewStage();
    }





}
