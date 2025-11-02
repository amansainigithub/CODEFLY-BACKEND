package com.coder.springjwt.services.adminServices.productManagerService.productApprovalService.imple;

import com.coder.springjwt.dtos.adminDtos.productRejectionReasonDto.ProductRejectionReasonDto;
import com.coder.springjwt.buckets.emailBucket.EmailService.emailSenderService.EmailSenderService;
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
    public ResponseEntity<?> productDisApproved(long productId , long reasonId , String description) {
        try {

            ProductRejectionReason productRejectionReason = this.productRejectionReasonRepo.findById(reasonId)
                    .orElseThrow(() -> new DataNotFoundException("Reason Id Not found :: " + reasonId));

            ProductDetailsModel productData = this.productDetailsRepo.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Product Id Not found :: " + productId));

            User user = userRepository.findByUsername(productData.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found Exception"));

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
            productData.setProductDisApprovedCode(productRejectionReason.getCode());
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

            //Mail Trigger Start....
            log.info("Mail Trigger Process Starting .. ");
            this.sendProductDisApprovalMail(productData , productRejectionReason , user.getSellerEmail());

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


    public void mailTrigger(String templateKey , Map<String,Object> mailSubject , Map<String,Object> mailBody, String emailTo)
    {
        this.emailSenderService.sendHtmlMail(templateKey ,mailSubject , mailBody , emailTo);
    }
}
