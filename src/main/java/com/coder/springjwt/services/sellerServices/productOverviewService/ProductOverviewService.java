package com.coder.springjwt.services.sellerServices.productOverviewService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductOverviewService {

    ResponseEntity<?> getUnderReviewProduct();
}
