package com.coder.springjwt.controllers.admin.productStages;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.services.adminServices.productStageServices.productBlockedStageService.ProductBlockedStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_BLOCKED_STAGE_CONTROLLER)
public class ProductBlockedStageController {

    @Autowired
    private ProductBlockedStageService productBlockedStageService;

    @GetMapping(AdminUrlMappings.PRODUCT_BLOCKED_STAGE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> productBlockedStage(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productBlockedStageService.productBlockedStage(page , size);
    }
}
