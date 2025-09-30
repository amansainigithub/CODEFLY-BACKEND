package com.coder.springjwt.services.sellerServices.productVariantService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.categories.VariantCategoryModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.repository.adminRepository.categories.VariantCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.services.sellerServices.productVariantService.ProductVariantService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductVariantServiceImple implements ProductVariantService {

    @Autowired
    private ProductDetailsRepo productDetailsRepo;

    @Override
    public ResponseEntity<?> loadProductDetails(long productId) {
        try {
            log.info("Get Type Category Data...");
            ProductDetailsModel productDetails = this.productDetailsRepo.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Data Not Found PRODUCT ID :: " + productId));
            return ResponseGenerator.generateSuccessResponse(productDetails , SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Error in Seller get Product Category ");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , SellerMessageResponse.ERROR);
        }
    }
}
