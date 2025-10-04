package com.coder.springjwt.services.adminServices.productStageServices.productApprovedStageService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductUnderReviewStageService {
    ResponseEntity<?> productUnderReviewStage();
}
