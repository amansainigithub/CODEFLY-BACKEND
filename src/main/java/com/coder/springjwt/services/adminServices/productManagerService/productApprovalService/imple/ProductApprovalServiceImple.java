package com.coder.springjwt.services.adminServices.productManagerService.productApprovalService.imple;

import com.coder.springjwt.emuns.seller.ProductStatus;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductRoot;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.services.adminServices.productManagerService.productApprovalService.ProductApprovalService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductApprovalServiceImple implements ProductApprovalService {

    @Autowired
    private ProductDetailsRepo productDetailsRepo;


    @Override
    public ResponseEntity<?> productApproved(long productId) {
        try {
            ProductDetailsModel productData = this.productDetailsRepo.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Product Id Not found :: " + productId));

            ProductRoot productRoot = productData.getProductRoot();
            productRoot.setProductStatus(ProductStatus.APPROVED.toString());

            productData.setProductStatus(ProductStatus.APPROVED.toString());
            this.productDetailsRepo.save(productData);

            log.info("Product Approved Successfully");
            return ResponseGenerator.generateSuccessResponse("SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }

    @Override
    public ResponseEntity<?> productDisApproved(long productId) {
        try {
            ProductDetailsModel productData = this.productDetailsRepo.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Product Id Not found :: " + productId));

            ProductRoot productRoot = productData.getProductRoot();
            productRoot.setProductStatus(ProductStatus.DIS_APPROVED.toString());

            productData.setProductStatus(ProductStatus.DIS_APPROVED.toString());
            this.productDetailsRepo.save(productData);

            log.info("Product Approved Successfully");
            return ResponseGenerator.generateSuccessResponse("SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }
}
