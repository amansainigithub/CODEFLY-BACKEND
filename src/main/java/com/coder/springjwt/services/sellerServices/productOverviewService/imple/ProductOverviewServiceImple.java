package com.coder.springjwt.services.sellerServices.productOverviewService.imple;

import com.coder.springjwt.dtos.sellerPayloads.productOverviewDtos.ProductDetailsOverviewDto;
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
import org.springframework.http.ResponseEntity;
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

    @Override
    public ResponseEntity<?> getUnderReviewProduct() {
        try {
            Map<String, String> currentUser = UserHelper.getCurrentUser();
            List<ProductDetailsModel> productDetailsList =   this.productDetailsRepo.
                                                    findByUsernameAndProductStatus(currentUser.get("username"),
                                                    ProductStatus.UNDER_REVIEW.toString());

            List<ProductDetailsOverviewDto> overviewList = new ArrayList<>();
            for(ProductDetailsModel pdm : productDetailsList)
            {
                ProductDetailsOverviewDto overviewDto  = new ProductDetailsOverviewDto();
                overviewDto.setId(pdm.getId());
                overviewDto.setProductName(pdm.getProductName());
                overviewDto.setUserId(pdm.getUserId());
                overviewDto.setProductStatus(pdm.getProductStatus());
                overviewDto.setProductKey(pdm.getProductKey());
                overviewDto.setProductDate(pdm.getProductDate());
                overviewDto.setProductTime(pdm.getProductTime());
                try {
                    overviewDto.setProductMainFile(pdm.getProductFiles().get(0).getFileUrl());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    overviewDto.setProductMainFile("BLANK");
                }
                overviewList.add(overviewDto);
            }
            return ResponseGenerator.generateSuccessResponse(overviewList , "SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("BAD REQUEST");
        }
    }
}
