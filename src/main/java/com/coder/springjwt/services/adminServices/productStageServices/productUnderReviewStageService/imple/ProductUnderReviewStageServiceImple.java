package com.coder.springjwt.services.adminServices.productStageServices.productUnderReviewStageService.imple;

import com.coder.springjwt.dtos.adminDtos.productStageDtos.ProductUnderReviewStageDto;
import com.coder.springjwt.emuns.seller.ProductStatus;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductFiles;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.services.adminServices.productStageServices.productUnderReviewStageService.ProductUnderReviewStageService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductUnderReviewStageServiceImple implements ProductUnderReviewStageService {


    @Autowired
    private ProductDetailsRepo productDetailsRepo;

    @Autowired
    private UserHelper userHelper;

    @Override
    public ResponseEntity<?> productUnderReviewStage(Integer page, Integer size) {
        try {
            log.info("ProductUnderReviewStageServiceImple working....");


            Page<ProductDetailsModel> productDetails = this.productDetailsRepo.findByProductStatus(
                    ProductStatus.UNDER_REVIEW.toString(),
                    PageRequest.of(page, size, Sort.by("productKey").descending())
            );

            List<ProductUnderReviewStageDto> dtoList = new ArrayList<>();

            for (ProductDetailsModel p : productDetails.getContent()) {

                String fileUrl = "BLANK";

                List<ProductFiles> productFiles = p.getProductFiles();

                if (productFiles != null && !productFiles.isEmpty()) {
                    ProductFiles pf = productFiles.get(0);
                    if (pf.getFileUrl() != null) {
                        fileUrl = pf.getFileUrl();
                    }
                }

                ProductUnderReviewStageDto dto = new ProductUnderReviewStageDto(
                        p.getId(),
                        p.getUserId(),
                        p.getProductName(),
                        p.getProductStatus(),
                        p.getProductKey(),
                        p.getProductDate(),
                        p.getProductTime(),
                        String.valueOf(p.getVariantId()),
                        fileUrl,
                        p.getProductSeries()
                );

                dtoList.add(dto);
            }

            // Response body with pagination info
            Map<String, Object> response = new HashMap<>();
            response.put("content", dtoList);
            response.put("currentPage", productDetails.getNumber());
            response.put("totalElements", productDetails.getTotalElements());
            response.put("totalPages", productDetails.getTotalPages());

            return ResponseGenerator.generateSuccessResponse(response , "Success");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("BAD REQUEST");
        }
    }



}
