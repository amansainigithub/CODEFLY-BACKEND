package com.coder.springjwt.controllers.admin.productStages;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.services.adminServices.productStageServices.productDisApprovedStageService.ProductDisApprovedStageService;
import com.coder.springjwt.services.adminServices.productStageServices.productDraftStageService.ProductDraftStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_DRAFT_STAGE_CONTROLLER)
public class ProductDraftStageController {

    @Autowired
    private ProductDraftStageService productDraftStageService;

    @GetMapping(AdminUrlMappings.PRODUCT_DRAFT_STAGE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> productDraftStage(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productDraftStageService.productDraftStage(page , size);
    }
}
