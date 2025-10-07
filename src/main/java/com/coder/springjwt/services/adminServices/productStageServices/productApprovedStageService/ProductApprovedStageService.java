package com.coder.springjwt.services.adminServices.productStageServices.productApprovedStageService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductApprovedStageService {

    ResponseEntity<?> productApprovedStage(Integer page, Integer size);
}
