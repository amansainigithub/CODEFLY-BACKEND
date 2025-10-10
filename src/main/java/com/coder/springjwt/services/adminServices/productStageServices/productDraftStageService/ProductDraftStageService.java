package com.coder.springjwt.services.adminServices.productStageServices.productDraftStageService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductDraftStageService {
    ResponseEntity<?> productDraftStage(Integer page, Integer size);
}
