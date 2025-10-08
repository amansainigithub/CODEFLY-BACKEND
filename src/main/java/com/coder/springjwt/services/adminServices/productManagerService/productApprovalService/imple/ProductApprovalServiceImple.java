package com.coder.springjwt.services.adminServices.productManagerService.productApprovalService.imple;

import com.coder.springjwt.dtos.adminDtos.productRejectionReasonDto.ProductRejectionReasonDto;
import com.coder.springjwt.emuns.seller.ProductStatus;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.helpers.generateDateandTime.GenerateDateAndTime;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.adminModels.ProductRejectionReasonModel.ProductRejectionReason;
import com.coder.springjwt.models.adminModels.productStatusTracker.ProductStatusTracker;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductRoot;
import com.coder.springjwt.repository.adminRepository.productStatusTracker.ProductStatusTrackerRepo;
import com.coder.springjwt.repository.productRejectionReasonRepo.ProductRejectionReasonRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.services.adminServices.productManagerService.productApprovalService.ProductApprovalService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductApprovalServiceImple implements ProductApprovalService {

    @Autowired
    private ProductDetailsRepo productDetailsRepo;

    @Autowired
    private ProductRejectionReasonRepo productRejectionReasonRepo;

    @Autowired
    private ProductStatusTrackerRepo productStatusTrackerRepo;

    @Autowired
    private ProductRejectionReasonRepo rejectionReasonRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserHelper userHelper;


    @Override
    public ResponseEntity<?> productApproved(long productId ) {
        try {
            ProductDetailsModel productData = this.productDetailsRepo.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Product Id Not found :: " + productId));

            //Product Root
            ProductRoot productRoot = productData.getProductRoot();
            productRoot.setProductStatus(ProductStatus.APPROVED.toString());

            //Product Details
            productData.setProductStatus(ProductStatus.APPROVED.toString());
            //Product Approved Date and Time
            String todayDate = GenerateDateAndTime.getTodayDate();
            productData.setProductApprovedDate(todayDate);
            String currentTime = GenerateDateAndTime.getCurrentTime();
            productData.setProductApprovedTime(currentTime);
            //Product Reason
            productData.setProductApprovedReason("APPROVED SUCCESSFULLY");
            //Approved By
            productData.setApprovedBy( userHelper.getCurrentUser().get("username") );
            this.productDetailsRepo.save(productData);
            log.info("Product Approved Successfully");

            //Set DisApproved Reason To Product Status Tracker Starting
            ProductStatusTracker productStatusTracker = new ProductStatusTracker();
            productStatusTracker.setProductId(productData.getId());
            productStatusTracker.setStatusDate(todayDate);
            productStatusTracker.setStatusTime(currentTime);
            productStatusTracker.setStatusDateTime(GenerateDateAndTime.getLocalDateTime());
            productStatusTracker.setReason("APPROVED SUCCESSFULLY");
            productStatusTracker.setReasonId(1l);
            productStatusTracker.setProductDescription("APPROVED SUCCESSFULLY");
            this.productStatusTrackerRepo.save(productStatusTracker);
            log.info("PRODUCT STATUS TRACKER SAVED SUCCESS");
            //Set DisApproved Reason To Product Status Tracker Ending...

            return ResponseGenerator.generateSuccessResponse("SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }

    @Override
    public ResponseEntity<?> productDisApproved(long productId , long reasonId , String description) {
        try {
            ProductRejectionReason productRejectionReason = this.productRejectionReasonRepo.findById(reasonId)
                    .orElseThrow(() -> new DataNotFoundException("Reason Id Not found :: " + reasonId));

            ProductDetailsModel productData = this.productDetailsRepo.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Product Id Not found :: " + productId));

            //Product Root
            ProductRoot productRoot = productData.getProductRoot();
            productRoot.setProductStatus(ProductStatus.DIS_APPROVED.toString());


            productData.setProductStatus(ProductStatus.DIS_APPROVED.toString());

            //Product Approved Date
            String todayDate = GenerateDateAndTime.getTodayDate();
            productData.setProductDisApprovedDate(todayDate);
            //Product Approved Time
            String currentTime = GenerateDateAndTime.getCurrentTime();
            productData.setProductDisApprovedTime(currentTime);
            //Dis-Approved Reason
            productData.setProductApprovedReason(productRejectionReason.getReason());
            //Dis-Approved Description
            productData.setProductDisApprovedDesc(description);
            //Approved By
            productData.setApprovedBy( userHelper.getCurrentUser().get("username") );
            this.productDetailsRepo.save(productData);
            log.info("Product Approved Successfully");

            //Set DisApproved Reason To Product Status Tracker Starting
            ProductStatusTracker productStatusTracker = new ProductStatusTracker();
            productStatusTracker.setProductId(productData.getId());
            productStatusTracker.setStatusDate(todayDate);
            productStatusTracker.setStatusTime(currentTime);
            productStatusTracker.setStatusDateTime(GenerateDateAndTime.getLocalDateTime());
            productStatusTracker.setReason(productRejectionReason.getReason());
            productStatusTracker.setReasonId(productRejectionReason.getId());
            productStatusTracker.setProductDescription(description);
            this.productStatusTrackerRepo.save(productStatusTracker);
            log.info("PRODUCT STATUS TRACKER SAVED SUCCESS");
            //Set DisApproved Reason To Product Status Tracker Ending...


            return ResponseGenerator.generateSuccessResponse("SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }

    @Override
    public ResponseEntity<?> getRejectionReasonsList() {
        try {
            List<ProductRejectionReason> rejectionReasonList =  this.rejectionReasonRepo.findAll();
            List<ProductRejectionReasonDto> rejectionReasonData = rejectionReasonList.stream()
                    .map(data -> modelMapper.map(data, ProductRejectionReasonDto.class))
                    .collect(Collectors.toList());
            return ResponseGenerator.generateSuccessResponse(rejectionReasonData,"SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Failed");
        }
    }
}
