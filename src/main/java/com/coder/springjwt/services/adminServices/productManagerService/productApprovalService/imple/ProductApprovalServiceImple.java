package com.coder.springjwt.services.adminServices.productManagerService.productApprovalService.imple;

import com.coder.springjwt.buckets.emailBucket.EmailService.emailSenderService.EmailSenderService;
import com.coder.springjwt.dtos.adminDtos.productRejectionReasonDto.ProductRejectionReasonDto;
import com.coder.springjwt.emuns.seller.ProductStatus;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.helpers.generateDateandTime.GenerateDateAndTime;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.adminModels.ProductRejectionReasonModel.ProductRejectionReason;
import com.coder.springjwt.models.adminModels.productStatusTracker.ProductStatusTracker;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductRoot;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.adminRepository.productStatusTracker.ProductStatusTrackerRepo;
import com.coder.springjwt.repository.productRejectionReasonRepo.ProductRejectionReasonRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.services.adminServices.productManagerService.productApprovalService.ProductApprovalService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserRepository userRepository;


    @Override
    public ResponseEntity<?> productApproved(long productId) {

        try {
            ProductDetailsModel productData = this.productDetailsRepo.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Product Id Not found :: " + productId));

            User user = userRepository.findByUsername(productData.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found Exception"));

            if ("VARIANT".equals(productData.getProductSeries())) {

                ProductDetailsModel mainProduct = this.productDetailsRepo
                        .findByProductKeyAndProductSeries(productData.getProductKey(), "MAIN")
                        .orElseThrow(() -> new DataNotFoundException("Main Product Not Found"));

                if ("UNDER_REVIEW".equals(mainProduct.getProductStatus())) {
                    return ResponseGenerator.generateBadRequestResponse(
                            new MessageResponse("First approve the main product, then approve the variant product.")
                    );
                }
            }

            //Product Approved Logic
            this.approveProduct(productData, user);

            log.info("Mail Trigger Process Starting...");
            this.sendProductApprovalMail(productData, user.getSellerEmail());

            return ResponseGenerator.generateSuccessResponse("SUCCESS");
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }

    /*Product Approval Logic */
    private void approveProduct(ProductDetailsModel productData, User user) {

        //Product Root Status
        ProductRoot productRoot = productData.getProductRoot();
        productRoot.setProductStatus(ProductStatus.APPROVED.toString());

        //product Data Status
        productData.setProductStatus(ProductStatus.APPROVED.toString());

        String todayDate = GenerateDateAndTime.getTodayDate();
        String currentTime = GenerateDateAndTime.getCurrentTime();

        productData.setProductApprovedDate(todayDate);
        productData.setProductApprovedTime(currentTime);
        productData.setProductApprovedReason("PRODUCT_APPROVED_SUCCESS");
        productData.setApprovedBy(user.getUsername());

        this.productDetailsRepo.save(productData);
        log.info("Product Approved Successfully");

        // Status Tracker
        ProductStatusTracker tracker = new ProductStatusTracker();
        tracker.setProductId(productData.getId());
        tracker.setStatusDate(todayDate);
        tracker.setStatusTime(currentTime);
        tracker.setStatusDateTime(GenerateDateAndTime.getLocalDateTime());
        tracker.setReason("APPROVED SUCCESSFULLY");
        tracker.setReasonId(1L);
        tracker.setProductDescription("APPROVED SUCCESSFULLY");

        this.productStatusTrackerRepo.save(tracker);
        log.info("PRODUCT STATUS TRACKER SAVED SUCCESSFULLY");
    }




    @Override
    public ResponseEntity<?> productDisApproved(long productId ,
                                                long reasonId ,
                                                String description,
                                                String rejectionRootCategory) {
        try {
            log.info("rejectionRootCategory :::" + rejectionRootCategory);

            //Check User and Product Details Start
            ProductDetailsModel productData = this.productDetailsRepo.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Product Id Not found :: " + productId));

            User user = userRepository.findByUsername(productData.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found Exception"));
            //Check User and Product Details Ending...


            //Product Rejection Reason
            ProductRejectionReason productRejectionReason = this.productRejectionReasonRepo.findById(reasonId)
                    .orElseThrow(() -> new DataNotFoundException("Reason Id Not found :: " + reasonId));



            //Root Rejection Category
            String rootRejectionCategory = productRejectionReason.getRootRejectionCategories().getRootRejectionCategory();
            // Validate Rejection Category
            if (!rootRejectionCategory.equals(ProductStatus.DIS_APPROVED.toString()) &&
                    !rootRejectionCategory.equals(ProductStatus.BLOCKED.toString())) {
                return ResponseGenerator.generateBadRequestResponse("Error | Mis-Match Product Status");
            }

            if(rootRejectionCategory.equals(ProductStatus.DIS_APPROVED.toString())) {
                //Product Root
                ProductRoot productRoot = productData.getProductRoot();
                    //Set Status To Product Root
                    productRoot.setProductStatus(ProductStatus.DIS_APPROVED.toString());

                    //Product Details Update Product Status
                    productData.setProductStatus(ProductStatus.DIS_APPROVED.toString());

                    // Fill Common Product Info
                    this.fillProductStatusDetails(productData, productRejectionReason, rootRejectionCategory);

                    // Save Tracker
                    this.saveProductStatusTracker(productData, productRejectionReason, description);

                    //Main Process Starting
                    log.info("Product Approval Main Trigger ");
                    this.sendProductDisApprovalMail(productData, productRejectionReason, user.getSellerEmail());

                    return ResponseGenerator.generateSuccessResponse("SUCCESS");
                }
            else if(rootRejectionCategory.equals(ProductStatus.BLOCKED.toString())) {
                //Product Root
                ProductRoot productRoot = productData.getProductRoot();

                //Set Status To Product Root
                productRoot.setProductStatus(ProductStatus.BLOCKED.toString());

                //Product Details Update Product Status
                productData.setProductStatus(ProductStatus.BLOCKED.toString());

                // Fill Common Product Info
                this.fillProductStatusDetails(productData, productRejectionReason, rootRejectionCategory);

                // Save Tracker
                this.saveProductStatusTracker(productData, productRejectionReason, description);

                //Main Process Starting
                log.info("Product Blocked Main Trigger");
                this.sendProductBlockedMail(productData, productRejectionReason, user.getSellerEmail());

                return ResponseGenerator.generateSuccessResponse("SUCCESS");
            }
            else {
                return ResponseGenerator.generateBadRequestResponse("Product Status Not Found Or Mis-Match");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }

    private void fillProductStatusDetails(ProductDetailsModel productData,
                                               ProductRejectionReason productRejectionReason,
                                               String rejectionRootCategory) {

        String todayDate = GenerateDateAndTime.getTodayDate();
        String currentTime = GenerateDateAndTime.getCurrentTime();

        productData.setProductDisApprovedDate(todayDate);
        productData.setProductDisApprovedTime(currentTime);
        productData.setProductApprovedReason(productRejectionReason.getReason());
        productData.setProductDisApprovedCode(productRejectionReason.getCode());
        productData.setProductRootRejectionCategory(rejectionRootCategory);
        productData.setApprovedBy(userHelper.getCurrentUser().get("username"));
        //Save Product Status and Approval Status Update
        this.productDetailsRepo.save(productData);
        log.info("Product Approved Successfully");
    }

    /**
     * Creates and saves a product status tracker record.
     */
    private void saveProductStatusTracker(ProductDetailsModel productData,
                                          ProductRejectionReason productRejectionReason,
                                          String description) {

        ProductStatusTracker tracker = new ProductStatusTracker();
        tracker.setProductId(productData.getId());
        tracker.setStatusDate(GenerateDateAndTime.getTodayDate());
        tracker.setStatusTime(GenerateDateAndTime.getCurrentTime());
        tracker.setStatusDateTime(GenerateDateAndTime.getLocalDateTime());
        tracker.setReason(productRejectionReason.getReason());
        tracker.setReasonId(productRejectionReason.getId());
        tracker.setProductDescription(description);
        tracker.setProductRootRejectionCategory(productRejectionReason.getRootRejectionCategories().getRootRejectionCategory());
        tracker.setProductRootRejectionCategoryId(
                String.valueOf(productRejectionReason.getRootRejectionCategories().getId())
        );
        productStatusTrackerRepo.save(tracker);
        log.info("PRODUCT STATUS TRACKER SAVED SUCCESSFULLY");
    }



    public void sendProductApprovalMail(ProductDetailsModel productData ,
                                        String emailTo)
    {
        Map<String,Object> mailSubject = new HashMap<>();
        mailSubject.put("productId",productData.getProductKey());
        Map<String,Object> mailBody = new HashMap<>();
        mailBody.put("productName",productData.getProductName());
        mailBody.put("productId",productData.getProductKey());
        mailBody.put("currentYear","2025");
        this.mailTrigger("PRODUCT_APPROVED_SUCCESS",mailSubject , mailBody , emailTo);
    }

    public void sendProductDisApprovalMail(ProductDetailsModel productData,
                                           ProductRejectionReason productRejectionReason,
                                           String emailTo)
    {
        Map<String,Object> mailSubject = new HashMap<>();
        mailSubject.put("productId",productData.getProductKey());
        Map<String,Object> mailBody = new HashMap<>();
        mailBody.put("productName",productData.getProductName());
        mailBody.put("productId",productData.getProductKey());
        mailBody.put("disapprovalReason",productRejectionReason.getReason());
        mailBody.put("disapprovalDescription",productRejectionReason.getDescription());
        mailBody.put("supportUrl","http://localhost:61795/admin/dashboard/");
        mailBody.put("currentYear","2025");
        this.mailTrigger("PRODUCT_DISAPPROVED" , mailSubject , mailBody ,emailTo);
    }

    public void sendProductBlockedMail(ProductDetailsModel productData,
                                       ProductRejectionReason productRejectionReason,
                                       String emailTo)
    {
        Map<String,Object> mailSubject = new HashMap<>();
        mailSubject.put("productId",productData.getProductKey());
        Map<String,Object> mailBody = new HashMap<>();
        mailBody.put("productName",productData.getProductName());
        mailBody.put("productId",productData.getProductKey());
        mailBody.put("blockReason",productRejectionReason.getReason());
        mailBody.put("blockDescription",productRejectionReason.getDescription());
        mailBody.put("supportUrl","http://localhost:61795/admin/dashboard/");
        mailBody.put("currentYear","2025");
        this.mailTrigger("PRODUCT_BLOCKED" , mailSubject , mailBody ,emailTo);
    }


    public void mailTrigger(String templateKey , Map<String,Object> mailSubject , Map<String,Object> mailBody, String emailTo)
    {
        this.emailSenderService.sendHtmlMail(templateKey ,mailSubject , mailBody , emailTo);
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
