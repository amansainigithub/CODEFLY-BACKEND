package com.coder.springjwt.services.sellerServices.productVariantService.imple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerPayloads.productDetailPayloads.ProductDetailsDto;
import com.coder.springjwt.emuns.seller.ProductStatus;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.adminModels.categories.VariantCategoryModel;
import com.coder.springjwt.models.adminModels.chargeConfigModels.ChargeConfig;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductFiles;
import com.coder.springjwt.models.sellerModels.productModels.ProductRoot;
import com.coder.springjwt.models.sellerModels.productModels.ProductSizeRows;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.adminRepository.categories.VariantCategoryRepo;
import com.coder.springjwt.repository.adminRepository.chargeConfigRepo.ChargeConfigRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductRootRepo;
import com.coder.springjwt.services.sellerServices.productVariantService.ProductVariantService;
import com.coder.springjwt.services.sellerServices.sellerProductService.imple.ProductServiceHelper;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class ProductVariantServiceImple implements ProductVariantService {

    @Autowired
    private VariantCategoryRepo variantCategoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRootRepo productRootRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BucketService bucketService;
    @Autowired
    private ProductServiceHelper productServiceHelper;
    @Autowired
    private UserHelper userHelper;
    @Autowired
    private ProductDetailsRepo productDetailsRepo;
    @Autowired
    private ChargeConfigRepo chargeConfigRepo;

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

    @Override
    public ResponseEntity<?> saveProductVariantDetails(ProductDetailsDto productDetailsDto, long variantId ,
                                                       long existingProductId) {
        log.info("SAVE PRODUCT VARIANTS DETAILS FLYING...");
        try {
            //CHECK VARIANT-ID
            VariantCategoryModel variantCategoryModel = checkVariantId(variantId);

            //CHARGE CONFIG TCS, TDS, SHIPPING CHARGE
            ChargeConfig chargeConfig = this.chargeConfigRepo.findByVariantId(String.valueOf(variantId))
                    .orElseThrow(() -> new DataNotFoundException("Variant Id Not Found ID :: " + variantId));

            if (variantCategoryModel != null) {
                //USER DETAILS
                Map<String, String> node = userHelper.getCurrentUser();
                User user = this.getUserDetails(node.get("username"));
                String userId = String.valueOf(user.getId());
                String userName = String.valueOf(user.getUsername());

                //Product ROOT DATA
                ProductRoot productRoot = new ProductRoot();
                productRoot.setVariantId(variantCategoryModel.getId());
                productRoot.setVariantName(variantCategoryModel.getCategoryName());
                productRoot.setUserId(String.valueOf(userId));
                productRoot.setUsername(String.valueOf(userName));
                productRoot.setProductStatus(ProductStatus.UNDER_REVIEW.toString());

                // CONVERT DTO TO MODEL PRODUCT DETAILS
                ProductDetailsModel productDetailsModel = modelMapper.map(productDetailsDto, ProductDetailsModel.class);
                productDetailsModel.setVariantId(variantCategoryModel.getId());
                productDetailsModel.setVariantName(variantCategoryModel.getCategoryName());
                productDetailsModel.setProductSeries("VARIANT");
                //USERID AND USERNAME
                productDetailsModel.setUserId(String.valueOf(userId));
                productDetailsModel.setUsername(String.valueOf(userName));
                //PRODUCT DATE AND TIME
                productDetailsModel.setProductDate(this.getFormatDate());
                productDetailsModel.setProductTime(this.getFormatTime());
                //PRODUCT STATUS
                productDetailsModel.setProductStatus(ProductStatus.UNDER_REVIEW.toString());


                //Set USERNAME AND USERID SET TO PRODUCT SIZE ROWS
                Map<String, String> priceAndMrp = this.productSizeRows(userId, userName,
                        productDetailsModel.getProductSizeRows(), productDetailsModel);
                String productPrice = priceAndMrp.get("productPrice");
                String productMrp   =   priceAndMrp.get("productMrp");

                //SET PRODUCT DETAILS TO ACTUAL-PRICE AND MRP
                productDetailsModel.setProductPrice(productPrice);
                productDetailsModel.setProductMrp(productMrp);


                //Calculate TAX [TDS,TCS,GST,BANK-SETTLEMENT] STARTING
                BigDecimal productGst = productServiceHelper.calculateGST(new BigDecimal(productPrice),
                                        new BigDecimal(productDetailsModel.getGst()));
                productDetailsModel.setProductGst(String.valueOf(productGst));
                BigDecimal productTcs = productServiceHelper.calculateTCS(new BigDecimal(productPrice),
                                        new BigDecimal(productDetailsModel.getGst()) , chargeConfig.getTcsCharge());
                productDetailsModel.setProductTcs(String.valueOf(productTcs));
                BigDecimal productTds = productServiceHelper.calculateTDS(new BigDecimal(productPrice) ,
                                        chargeConfig.getTdsCharge());
                productDetailsModel.setProductTds(String.valueOf(productTds));

                BigDecimal bankSettlementAmount = productServiceHelper.bankSettlement(new BigDecimal(productPrice),
                                                  productGst, productTcs, productTds);
                productDetailsModel.setBankSettlementAmount(String.valueOf(bankSettlementAmount));
                //Calculate TAX Information Ending....


                //SHIPPING CHARGES
                float shippingCharges = Float.parseFloat(chargeConfig.getShippingCharge());
                float shippingFee = Float.parseFloat(chargeConfig.getShippingChargeFee());;
                float shippingTotal = shippingCharges + shippingFee;
                productDetailsModel.setShippingCharges(String.valueOf(shippingCharges));
                productDetailsModel.setShippingFee(String.valueOf(shippingFee));
                productDetailsModel.setShippingTotal(String.valueOf(shippingTotal));
                productDetailsModel.setBankSettlementWithShipping(
                        String.valueOf(bankSettlementAmount.add(BigDecimal.valueOf(shippingTotal))));
                //SHIPPING CHARGES ENDING...


                // PRODUCT DISCOUNT %
                float productDiscount = productServiceHelper.calculateDiscountPercent(Float.parseFloat(productMrp),
                        Float.parseFloat(productPrice));
                productDetailsModel.setProductDiscount(String.valueOf(productDiscount));


                //PRODUCT ROOT TO PRODUCT-DETAILS-MODEL
                productDetailsModel.setProductRoot(productRoot);

                //PRODUCT-DETAILS TO PRODUCT ROOT
                productRoot.setProductDetailsModels(List.of(productDetailsModel));

                //SAVE PRODUCT DETAILS
                ProductRoot productData = this.productRootRepo.save(productRoot);

                Map<Object, Object> productNode = new HashMap<>();
                productNode.put("id", productData.getProductDetailsModels().get(0).getId());
                return ResponseGenerator.generateSuccessResponse(productNode, "SUCCESS");
            } else {
                return ResponseGenerator.generateBadRequestResponse();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }






    @Override
    public ResponseEntity<?> saveProductVariantFiles(MultipartFile[] files, MultipartFile video, long newProductId ,
                                                     long existingProductId) {
        try {
            log.info("SAVE PRODUCT VARIANTS FILES DATA FLYING...");
            ProductDetailsModel existingProductDetails = this.productDetailsRepo.findById(existingProductId)
                    .orElseThrow(() -> new DataNotFoundException("Data Not Found Exception existingProductId"));


            ProductDetailsModel productDetails = this.productDetailsRepo.findById(newProductId)
                    .orElseThrow(() -> new DataNotFoundException("Data Not Found Exception"));

            // ============ IMAGE HANDLING ============
            ResponseEntity<?> imageResponse = checkAndSaveImages(files);
            if (imageResponse != null) {
                return imageResponse;
            } else {
                if (files.length > 0) {
                    List<ProductFiles> productFilesList = new ArrayList<>();

                    // SAVE IMAGES
                    log.info("Files Upload to Cloudinary Cloud Start");
                    for (MultipartFile file : files) {
                        // Upload image to Cloudinary
                        BucketModel bucketModel = this.bucketService.uploadCloudinaryFile(file, "IMAGE");

                        // Create ProductFiles object
                        ProductFiles productFiles = new ProductFiles();
                        productFiles.setFileSize(file.getSize());
                        productFiles.setContentType(file.getContentType());
                        productFiles.setFileType("IMAGE");
                        productFiles.setFileUrl(bucketModel.getBucketUrl());
                        productFiles.setFileName(bucketModel.getFileName());
                        productFiles.setProductDetailsModel(productDetails);
                        productFilesList.add(productFiles);
                    }

                    // REPLACE OLD FILES TO NEW ONE
                    productDetails.getProductFiles().clear();
                    productDetails.getProductFiles().addAll(productFilesList);
                    this.productDetailsRepo.save(productDetails);
                    log.info("========= IMAGES SAVED SUCCESSFULLY =========");
                }
            }

            // ============ VIDEO HANDLING ============
            ResponseEntity<?> videoResponse = checkIsVideoValid(video);
            if (videoResponse != null) {
                return videoResponse;
            } else {
                if (video != null && !video.isEmpty()) {
                    log.info("Video Upload to Cloudinary Cloud Start");

                    // Upload video to Cloudinary
                    BucketModel bucketModel = this.bucketService.uploadCloudinaryFile(video,"VIDEO");

                    // Create ProductFiles object
                    ProductFiles productVideo = new ProductFiles();
                    productVideo.setFileSize(video.getSize());
                    productVideo.setContentType(video.getContentType());
                    productVideo.setFileType("VIDEO");
                    productVideo.setFileUrl(bucketModel.getBucketUrl());
                    productVideo.setFileName(bucketModel.getFileName());
                    productVideo.setProductDetailsModel(productDetails);

                    // Add video without removing images
                    productDetails.getProductFiles().add(productVideo);
                    this.productDetailsRepo.save(productDetails);
                    log.info("Video Upload to Cloudinary Cloud Ending");
                    log.info("========= VIDEO SAVED SUCCESSFULLY =========");
                }
            }

            //SAVE PRODUCT-KEY AND BIND DATA
            this.saveProductKeysBindingVariant(productDetails.getProductRoot() , existingProductDetails.getProductKey());
            log.info("Product-Variant SAVED SUCCESS...");
            return ResponseGenerator.generateSuccessResponse("Files uploaded successfully for productId: " + newProductId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Something went wrong while saving product files");
        }
    }


    // ================= FILE HANDLING =================
    public ResponseEntity<?> checkAndSaveImages(MultipartFile[] files) {
        List<String> allowedImageExtensions = Arrays.asList("jpg", "jpeg", "png");
        long maxImageSize = 5 * 1024 * 1024; // 5 MB

        int index = 1;
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                log.info("Slot " + index + " is empty");
                index++;
                continue;
            }

            String fileName = file.getOriginalFilename();
            if (fileName == null || !fileName.contains(".")) {
                return ResponseEntity.badRequest().body("Invalid file name at slot " + index);
            }

            String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (!allowedImageExtensions.contains(ext)) {
                return ResponseEntity.badRequest().body("Invalid file type at slot " + index + " (" + fileName + "). Only JPG and PNG allowed.");
            }

            if (file.getSize() > maxImageSize) {
                return ResponseEntity.badRequest().body("File " + fileName + " at slot " + index + " exceeds the 1 MB size limit.");
            }

            // Save image
            log.info("Image accepted at slot " + index + " :: " + fileName + " | Size: " + file.getSize());

            index++;
        }
        return null; // null means SUCCESS
    }

    // ================= VIDEO HANDLING =================
    public ResponseEntity<?> checkIsVideoValid(MultipartFile video) {
        if (video == null || video.isEmpty()) {
            log.info("No video uploaded.");
            return null; // optional: return ResponseEntity.badRequest().body("Video is required");
        }

        String videoName = video.getOriginalFilename();
        if (videoName == null || !videoName.contains(".")) {
            return ResponseEntity.badRequest().body("Invalid video file name");
        }

        String videoExt = videoName.substring(videoName.lastIndexOf(".") + 1).toLowerCase();
        List<String> allowedVideoExtensions = Arrays.asList("mp4");

        long minVideoSize = 1 * 1024 * 1024;   // 1 MB
        long maxVideoSize = 50 * 1024 * 1024;  // 10 MB

        if (!allowedVideoExtensions.contains(videoExt)) {
            return ResponseEntity.badRequest().body("Invalid video type (" + videoName + "). Only MP4 allowed.");
        }

        if (video.getSize() < minVideoSize) {
            return ResponseEntity.badRequest().body("Video " + videoName + " must be at least 1 MB in size.");
        }

        if (video.getSize() > maxVideoSize) {
            return ResponseEntity.badRequest().body("Video " + videoName + " exceeds the 50 MB size limit.");
        }

        // Save video
        log.info("Video accepted :: " + videoName + " | Size: " + video.getSize());

        return null; // null means SUCCESS
    }




    private Map<String,String> productSizeRows(String userId, String username,
                                               List<ProductSizeRows> productSizeRows,
                                               ProductDetailsModel productDetailsModel )
    {
        String productPrice = null;
        String productMrp = null;
        int count = 0;
        for (ProductSizeRows sizeRows : productSizeRows) {
            if(count == 0)
            {
                productPrice = sizeRows.getPrice();
                productMrp = sizeRows.getMrp();
            }
            sizeRows.setProductDetailsModel(productDetailsModel);
            //Set UserId and UserName
            sizeRows.setUserId(String.valueOf(userId));
            sizeRows.setUsername(String.valueOf(username));
            count++;
        }
        Map<String,String> priceAndMrp = new HashMap<>();
        priceAndMrp.put("productPrice",productPrice);
        priceAndMrp.put("productMrp",productMrp);
        return priceAndMrp;
    }




    public void saveProductKeysBindingVariant(ProductRoot productRoot , String productKeyExisting) {
        try {
            long productId = productRoot.getId();

            //Using Existing Product Keys
            String productKey = productKeyExisting;
            //Set Product Key to Product Root
            productRoot.setProductKey(productKey);

            for (ProductDetailsModel pdm : productRoot.getProductDetailsModels()) {
                pdm.setProductRootId(productId);
                pdm.setProductKey(productKey);

                for (ProductFiles pf : pdm.getProductFiles()) {
                    pf.setProductRootId(productId);
                    pf.setProductKey(productKey);
                    pf.setProductDetailsId(pdm.getId());
                }

                for (ProductSizeRows psr : pdm.getProductSizeRows()){
                    psr.setProductRootId(productId);
                    psr.setProductKey(productKey);
                    psr.setProductDetailsId(pdm.getId());
                }
            }
            //save Product Root with Product-Id and OProduct Key Binds
            this.productRootRepo.save(productRoot);
        } catch (Exception e) {
            log.error("Exception generate in saveProductKeysBinding");
            e.getMessage();
            e.printStackTrace();
        }
    }

    public VariantCategoryModel checkVariantId(long variantId) {
        try {
            VariantCategoryModel variantCategoryData = this.variantCategoryRepo.findById(variantId).orElseThrow(() -> new DataNotFoundException("Variant Category Id not found :: " + variantId));
            return variantCategoryData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataNotFoundException("Variant Category Id not found :: " + variantId);
        }
    }

    private User getUserDetails(String username) {
        try {
            return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found.."));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFormatDate() {
        // Current Date
        LocalDate today = LocalDate.now();
        // Formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
        // Convert
        String formattedDate = today.format(formatter);
        return formattedDate;
    }

    public String getFormatTime() {
        // Current Time
        LocalTime now = LocalTime.now();
        // Formatter -> h:mm a  (12-hour with AM/PM)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
        // Format time
        String formattedTime = now.format(formatter);
        return formattedTime;
    }



}
