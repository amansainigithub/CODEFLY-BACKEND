package com.coder.springjwt.controllers.admin.productStages;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.services.adminServices.productStageServices.productApprovedStageService.ProductApprovedStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_APPROVED_STAGE_CONTROLLER)
public class ProductApprovedStageController {

    @Autowired
    private ProductApprovedStageService productApprovedStageService;

    @GetMapping(AdminUrlMappings.PRODUCT_APPROVED_STAGE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> productApprovedStage(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productApprovedStageService.productApprovedStage(page , size);
    }
}
