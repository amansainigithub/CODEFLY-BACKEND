package com.coder.springjwt.services.adminServices.productStageServices.productApprovedStageService.imple;

import com.coder.springjwt.dtos.adminDtos.productStageDtos.ProductUnderReviewStageDto;
import com.coder.springjwt.dtos.sellerPayloads.productOverviewDtos.ProductDetailsOverviewDto;
import com.coder.springjwt.emuns.seller.ProductStatus;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.services.adminServices.productStageServices.productApprovedStageService.ProductUnderReviewStageService;
import com.coder.springjwt.services.sellerServices.productOverviewService.imple.ProductOverviewServiceImple;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ProductUnderReviewStageServiceImple implements ProductUnderReviewStageService {


    @Autowired
    private ProductDetailsRepo productDetailsRepo;

    @Override
    public ResponseEntity<?> productUnderReviewStage() {
        try {
            log.info(ProductOverviewServiceImple.class.getName() + " working....");

            List<ProductDetailsModel> productDetailsData = this.productDetailsRepo
                    .findByProductStatus(ProductStatus.UNDER_REVIEW.toString() , Sort.by("productKey").descending());

            List<ProductUnderReviewStageDto> underReviewDto = new ArrayList<>();

            for(ProductDetailsModel pdm : productDetailsData)
            {
                ProductUnderReviewStageDto pdmDto = new ProductUnderReviewStageDto();
                pdmDto.setId(pdm.getId());
                pdmDto.setUserId(pdm.getUserId());
                pdmDto.setProductName(pdm.getProductName());
                pdmDto.setProductStatus(pdm.getProductStatus());
                pdmDto.setProductKey(pdm.getProductKey());
                pdmDto.setProductDate(pdm.getProductDate());
                pdmDto.setProductTime(pdm.getProductTime());
                pdmDto.setVariantId(String.valueOf(pdm.getVariantId()));
                pdmDto.setProductSeries(pdm.getProductSeries());
                try {
                    pdmDto.setProductMainFile(pdm.getProductFiles().get(0).getFileUrl());
                } catch (Exception e) {
                    pdmDto.setProductMainFile("BLANK");
                }

                //ADD TO LIST
                underReviewDto.add(pdmDto);
            }


            return ResponseGenerator.generateSuccessResponse(underReviewDto, "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("BAD REQUEST");
        }
    }
}
