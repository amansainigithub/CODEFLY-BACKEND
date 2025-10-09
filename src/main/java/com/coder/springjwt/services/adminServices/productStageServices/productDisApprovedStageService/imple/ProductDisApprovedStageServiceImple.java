package com.coder.springjwt.services.adminServices.productStageServices.productDisApprovedStageService.imple;

import com.coder.springjwt.dtos.adminDtos.productStageDtos.ProductDisApprovedStageDto;
import com.coder.springjwt.emuns.seller.ProductStatus;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.services.adminServices.productStageServices.productDisApprovedStageService.ProductDisApprovedStageService;
import com.coder.springjwt.services.adminServices.productStageServices.productUnderReviewStageService.imple.ProductUnderReviewStageServiceImple;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductDisApprovedStageServiceImple implements ProductDisApprovedStageService {

    @Autowired
    private ProductDetailsRepo productDetailsRepo;

    @Override
    public ResponseEntity<?> productDisApprovedStage(Integer page, Integer size) {
        try {
            log.info("Data fetch Success :::: {}" + ProductUnderReviewStageServiceImple.class.getName());

            Page<ProductDetailsModel> productDetails = this.productDetailsRepo.findByProductStatus(
                    ProductStatus.DIS_APPROVED.toString(),
                    PageRequest.of(page, size, Sort.by("productKey").descending())
            );

            // DTO list
            List<ProductDisApprovedStageDto> dtoList = productDetails.getContent()
                    .stream()
                    .map(p -> new ProductDisApprovedStageDto(
                            p.getId(),
                            p.getUserId(),
                            p.getProductName(),
                            p.getProductStatus(),
                            p.getProductKey(),
                            p.getProductDate(),
                            p.getProductTime(),
                            String.valueOf(p.getVariantId()),
                            (p.getProductFiles().get(0).getFileUrl() != null )?
                                    p.getProductFiles().get(0).getFileUrl() : "BLANK",
                            p.getProductSeries()
                    ))
                    .collect(Collectors.toList());

            // Response body with pagination info
            Map<String, Object> response = new HashMap<>();
            response.put("content", dtoList);
            response.put("currentPage", productDetails.getNumber());
            response.put("totalItems", productDetails.getTotalElements());
            response.put("totalPages", productDetails.getTotalPages());

            return ResponseGenerator.generateSuccessResponse(response , "Success");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("BAD REQUEST");
        }
    }
}
