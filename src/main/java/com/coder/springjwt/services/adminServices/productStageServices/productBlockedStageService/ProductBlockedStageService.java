package com.coder.springjwt.services.adminServices.productStageServices.productBlockedStageService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductBlockedStageService {
    ResponseEntity<?> productBlockedStage(Integer page, Integer size);
}
