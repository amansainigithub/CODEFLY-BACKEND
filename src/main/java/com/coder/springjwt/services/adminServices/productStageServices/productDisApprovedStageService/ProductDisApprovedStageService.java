package com.coder.springjwt.services.adminServices.productStageServices.productDisApprovedStageService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ProductDisApprovedStageService {
    ResponseEntity<?> productDisApprovedStage(Integer page, Integer size);
}
