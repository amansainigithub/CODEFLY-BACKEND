package com.coder.springjwt.services.sellerServices.productOverviewService.imple;

import com.coder.springjwt.dtos.sellerPayloads.productDetailPayloads.ProductDetailsDto;
import com.coder.springjwt.dtos.sellerPayloads.productOverviewDtos.ProductDetailsOverviewDto;
import com.coder.springjwt.emuns.seller.ProductStatus;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductSizeRows;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductRootRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductSizeRowsRepo;
import com.coder.springjwt.services.sellerServices.productOverviewService.ProductOverviewService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

@Service
@Slf4j
public class ProductOverviewServiceImple implements ProductOverviewService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductDetailsRepo productDetailsRepo;
    @Autowired
    private ProductSizeRowsRepo productSizeRowsRepo;
    @Autowired
    private ProductRootRepo productRootRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserHelper userHelper;

    @Override
    public ResponseEntity<?> getUnderReviewProduct(Integer page, Integer size , String username) {
        try {
            log.info(ProductOverviewServiceImple.class.getName() + " working....");

            // VALIDATE CURRENT USER
            Map<String, String> currentUser = userHelper.getCurrentUser();
            if(!currentUser.get("username").trim().equals(username.trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }

            Page<ProductDetailsModel> productDetailsPage = this.productDetailsRepo
                    .findByUsernameAndProductStatus(
                            currentUser.get("username"),
                            ProductStatus.UNDER_REVIEW.toString(),
                            PageRequest.of(page, size, Sort.by("productKey").descending())
                    );

            List<ProductDetailsOverviewDto> overviewList = new ArrayList<>();
            for (ProductDetailsModel pdm : productDetailsPage) {
                ProductDetailsOverviewDto overviewDto = new ProductDetailsOverviewDto();
                overviewDto.setId(pdm.getId());
                overviewDto.setProductName(pdm.getProductName());
                overviewDto.setUserId(pdm.getUserId());
                overviewDto.setProductStatus(pdm.getProductStatus());
                overviewDto.setProductKey(pdm.getProductKey());
                overviewDto.setProductDate(pdm.getProductDate());
                overviewDto.setProductTime(pdm.getProductTime());
                overviewDto.setVariantId(String.valueOf(pdm.getVariantId()));
                overviewDto.setProductSeries(pdm.getProductSeries());
                try {
                    overviewDto.setProductMainFile(pdm.getProductFiles().get(0).getFileUrl());
                } catch (Exception e) {
                    overviewDto.setProductMainFile("BLANK");
                }
                overviewList.add(overviewDto);
            }

            // DTO list ko Page me wrap karna
            Page<ProductDetailsOverviewDto> overviewPageData =
                    new PageImpl<>(overviewList, productDetailsPage.getPageable(), productDetailsPage.getTotalElements());

            return ResponseGenerator.generateSuccessResponse(overviewPageData, "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("BAD REQUEST");
        }
    }

    @Override
    public ResponseEntity<?> getApprovedProduct(Integer page, Integer size, String username) {
        try {
            log.info(ProductOverviewServiceImple.class.getName() + " working....");

            // VALIDATE CURRENT USER
            Map<String, String> currentUser = userHelper.getCurrentUser();
            if(!currentUser.get("username").trim().equals(username.trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }

            Page<ProductDetailsModel> productDetailsPage = this.productDetailsRepo
                    .findByUsernameAndProductStatus(
                            currentUser.get("username"),
                            ProductStatus.APPROVED.toString(),
                            PageRequest.of(page, size, Sort.by("productKey").descending())
                    );

            List<ProductDetailsOverviewDto> overviewList = new ArrayList<>();
            for (ProductDetailsModel pdm : productDetailsPage) {
                ProductDetailsOverviewDto overviewDto = new ProductDetailsOverviewDto();
                overviewDto.setId(pdm.getId());
                overviewDto.setProductName(pdm.getProductName());
                overviewDto.setUserId(pdm.getUserId());
                overviewDto.setProductStatus(pdm.getProductStatus());
                overviewDto.setProductKey(pdm.getProductKey());
                overviewDto.setProductDate(pdm.getProductDate());
                overviewDto.setProductTime(pdm.getProductTime());
                overviewDto.setVariantId(String.valueOf(pdm.getVariantId()));
                try {
                    overviewDto.setProductMainFile(pdm.getProductFiles().get(0).getFileUrl());
                } catch (Exception e) {
                    overviewDto.setProductMainFile("BLANK");
                }
                overviewList.add(overviewDto);
            }

            // DTO list ko Page me wrap karna
            Page<ProductDetailsOverviewDto> overviewPageData =
                    new PageImpl<>(overviewList, productDetailsPage.getPageable(), productDetailsPage.getTotalElements());

            return ResponseGenerator.generateSuccessResponse(overviewPageData, "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("BAD REQUEST");
        }
    }



}
