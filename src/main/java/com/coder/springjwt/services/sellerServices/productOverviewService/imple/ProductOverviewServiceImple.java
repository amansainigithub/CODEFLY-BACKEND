package com.coder.springjwt.services.sellerServices.productOverviewService.imple;

import com.coder.springjwt.dtos.sellerPayloads.productOverviewDtos.ProductDetailsOverviewDto;
import com.coder.springjwt.dtos.sellerPayloads.productOverviewDtos.ProductDisApprovedOverviewDto;
import com.coder.springjwt.dtos.sellerPayloads.productOverviewDtos.ProductDraftOverviewDto;
import com.coder.springjwt.dtos.sellerPayloads.productOverviewDtos.UserAllProductOverviewDto;
import com.coder.springjwt.emuns.seller.ProductStatus;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
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
import java.util.HashMap;
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



    @Override
    public ResponseEntity<?> getDisApprovedProduct(Integer page, Integer size, String username) {
        try {
            log.info(ProductOverviewServiceImple.class.getName() + " working....");

            // VALIDATE CURRENT USER
            Map<String, String> currentUser = userHelper.getCurrentUser();
            System.out.println("Seller Name  :: " + currentUser.get("username"));
            if(!currentUser.get("username").trim().equals(username.trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }

            Page<ProductDetailsModel> productDetailsPage = this.productDetailsRepo
                    .findByUsernameAndProductStatus(
                            currentUser.get("username"),
                            ProductStatus.DIS_APPROVED.toString(),
                            PageRequest.of(page, size, Sort.by("productKey").descending())
                    );

            List<ProductDisApprovedOverviewDto> overviewList = new ArrayList<>();
            for (ProductDetailsModel pdm : productDetailsPage) {
                ProductDisApprovedOverviewDto overviewDto = new ProductDisApprovedOverviewDto();
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

            // DTO list ko Page Wrapping
            Page<ProductDisApprovedOverviewDto> overviewPageData =
                    new PageImpl<>(overviewList, productDetailsPage.getPageable(), productDetailsPage.getTotalElements());

            return ResponseGenerator.generateSuccessResponse(overviewPageData, "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("BAD REQUEST");
        }
    }

    @Override
    public ResponseEntity<?> getDraftProduct(Integer page, Integer size, String username) {
        try {
            log.info(ProductOverviewServiceImple.class.getName() + " working....");

            // VALIDATE CURRENT USER
            Map<String, String> currentUser = userHelper.getCurrentUser();
            System.out.println("Seller Name  :: " + currentUser.get("username"));
            if(!currentUser.get("username").trim().equals(username.trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }

            Page<ProductDetailsModel> productDetailsPage = this.productDetailsRepo
                    .findByUsernameAndProductStatus(
                            currentUser.get("username"),
                            ProductStatus.DRAFT.toString(),
                            PageRequest.of(page, size, Sort.by("productKey").descending())
                    );

            List<ProductDraftOverviewDto> overviewList = new ArrayList<>();
            for (ProductDetailsModel pdm : productDetailsPage) {
                ProductDraftOverviewDto overviewDto = new ProductDraftOverviewDto();
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
            // DTO list ko Page Wrapping
            Page<ProductDraftOverviewDto> overviewPageData =
                    new PageImpl<>(overviewList, productDetailsPage.getPageable(), productDetailsPage.getTotalElements());

            return ResponseGenerator.generateSuccessResponse(overviewPageData, "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("BAD REQUEST");
        }
    }

    @Override
    public ResponseEntity<?> fetchAllUserProduct(Integer page, Integer size, String username) {

        try {
            log.info(ProductOverviewServiceImple.class.getName() + " working....");

            // VALIDATE CURRENT USER
            Map<String, String> currentUser = userHelper.getCurrentUser();
            System.out.println("Seller Name  :: " + currentUser.get("username"));
            if(!currentUser.get("username").trim().equals(username.trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }

            Page<ProductDetailsModel> userProductData = this.productDetailsRepo
                                            .findByUsername( currentUser.get("username"),
                                             PageRequest.of(page, size, Sort.by("productKey").descending()) );

            List<UserAllProductOverviewDto> overviewList = new ArrayList<>();

            for (ProductDetailsModel pdm : userProductData) {
                UserAllProductOverviewDto overviewDto = new UserAllProductOverviewDto();
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

            // DTO list ko Page Wrapping
            Page<UserAllProductOverviewDto> overviewPageData =
                    new PageImpl<>(overviewList, userProductData.getPageable(), userProductData.getTotalElements());

            //Product Count
            int totalProducts = Integer.parseInt(String.valueOf(userProductData.getTotalElements()));
            int underReviewCount = productDetailsRepo.countByProductStatusAndUsername(ProductStatus.UNDER_REVIEW.toString(), username);
            int approvedCount = productDetailsRepo.countByProductStatusAndUsername(ProductStatus.APPROVED.toString(), username);
            int disApprovedCount = productDetailsRepo.countByProductStatusAndUsername(ProductStatus.DIS_APPROVED.toString(), username);
            int draftCount = productDetailsRepo.countByProductStatusAndUsername(ProductStatus.DRAFT.toString(), username);

            Map<String,Object> productData = new HashMap<>();
            productData.put("totalProducts",totalProducts);
            productData.put("underReviewCount",underReviewCount);
            productData.put("approvedCount",approvedCount);
            productData.put("disApprovedCount",disApprovedCount);
            productData.put("draftCount",draftCount);
            productData.put("overviewPageData",overviewPageData);


            return ResponseGenerator.generateSuccessResponse(productData, "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("BAD REQUEST");
        }
    }

}
