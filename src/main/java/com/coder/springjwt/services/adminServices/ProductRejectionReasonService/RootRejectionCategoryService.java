package com.coder.springjwt.services.adminServices.ProductRejectionReasonService;

import com.coder.springjwt.dtos.adminDtos.productRejectionReasonDto.RootRejectionCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface RootRejectionCategoryService {
    ResponseEntity<?> createRootRejectionCategory(RootRejectionCategoryDto rootRejectionCategoryDto);

    ResponseEntity<?> getRootRejectionCategory();

    ResponseEntity<?> deleteRootRejectionCategory(long rejectionId);

    ResponseEntity<?> getRootRejectionCategoryById(long rejectionId);

    ResponseEntity<?> updateRootRejectionCategory(RootRejectionCategoryDto rootRejectionCategoryDto);

}
