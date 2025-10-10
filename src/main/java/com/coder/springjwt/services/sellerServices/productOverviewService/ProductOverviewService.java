package com.coder.springjwt.services.sellerServices.productOverviewService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductOverviewService {

    ResponseEntity<?> getUnderReviewProduct(Integer page, Integer size , String username);

    ResponseEntity<?> getApprovedProduct(Integer page, Integer size, String username);

    ResponseEntity<?> getDisApprovedProduct(Integer page, Integer size, String username);

    ResponseEntity<?> getDraftProduct(Integer page, Integer size, String username);

    ResponseEntity<?> fetchAllUserProduct(Integer page, Integer size, String username);
}
