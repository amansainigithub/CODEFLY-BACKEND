package com.coder.springjwt.services.adminServices.productManagerService.productApprovalService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductApprovalService {

    ResponseEntity<?> productApproved(long productId);

    ResponseEntity<?> productDisApproved(long productId ,long reasonId , String description);

    ResponseEntity<?> getRejectionReasonsList();
}
