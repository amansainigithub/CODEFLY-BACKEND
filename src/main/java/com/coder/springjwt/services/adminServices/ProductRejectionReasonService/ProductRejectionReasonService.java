package com.coder.springjwt.services.adminServices.ProductRejectionReasonService;

import com.coder.springjwt.dtos.adminDtos.productRejectionReasonDto.ProductRejectionReasonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ProductRejectionReasonService {

    ResponseEntity<?> createRejectionReason(ProductRejectionReasonDto productRejectionReasonDto);

    ResponseEntity<?> getRejectionReasons();

    ResponseEntity<?> deleteRejectionReason(long rejectionId);

    ResponseEntity<?> getRejectionReasonById(long rejectionId);

    ResponseEntity<?> updateRejectionReason(ProductRejectionReasonDto productRejectionReasonDto);
}
