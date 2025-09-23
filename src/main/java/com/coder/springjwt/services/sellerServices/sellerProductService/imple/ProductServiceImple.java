package com.coder.springjwt.services.sellerServices.sellerProductService.imple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.dtos.sellerPayloads.productDetailPayloads.ProductDetailsDto;
import com.coder.springjwt.emuns.seller.ProductStatus;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.adminModels.categories.VariantCategoryModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductFiles;
import com.coder.springjwt.models.sellerModels.productModels.ProductRoot;
import com.coder.springjwt.models.sellerModels.productModels.ProductSizeRows;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.adminRepository.categories.VariantCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductRootRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductSizeRowsRepo;
import com.coder.springjwt.services.sellerServices.sellerProductService.ProductService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class ProductServiceImple implements ProductService {

    @Autowired
    private VariantCategoryRepo variantCategoryRepo;
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
    private BucketService bucketService;
    @Autowired
    private ProductServiceHelper productServiceHelper;

    @Override
    public ResponseEntity<?> saveProductDetails(ProductDetailsDto productDetailsDto, long variantId) {
        log.info("saveProductDetails........");
        try {
            VariantCategoryModel variantCategoryModel = checkVariantId(variantId);

            if (variantCategoryModel == null) {
                return ResponseGenerator.generateBadRequestResponse();
            }

            if (variantCategoryModel != null) {
                //Get User
                Map<String, String> node = UserHelper.getCurrentUser();
                User username = this.getUserDetails(node.get("username"));

                //Product Root Data...
                ProductRoot productRoot = new ProductRoot();
                productRoot.setVariantId(variantCategoryModel.getId());
                productRoot.setVariantName(variantCategoryModel.getCategoryName());

                //Set UserId and UserName
                productRoot.setUserId(String.valueOf(username.getId()));
                productRoot.setUsername(String.valueOf(username.getUsername()));

                //Product Status
                productRoot.setProductStatus(ProductStatus.UNDER_REVIEW.toString());

                //Convert Data To mapper PRODUCT DETAILS
                ProductDetailsModel productDetailsModel = modelMapper.map(productDetailsDto, ProductDetailsModel.class);
                productDetailsModel.setVariantId(variantCategoryModel.getId());
                productDetailsModel.setVariantName(variantCategoryModel.getCategoryName());
                productDetailsModel.setProductSeries("MAIN");
                //Set UserId and UserName
                productDetailsModel.setUserId(String.valueOf(username.getId()));
                productDetailsModel.setUsername(String.valueOf(username.getUsername()));

                //Set Product Root to Product Details Model
                productDetailsModel.setProductRoot(productRoot);

                //SAVE FORMAT PRODUCT DATE AND TIME
                productDetailsModel.setProductDate(this.getFormatDate());
                productDetailsModel.setProductTime(this.getFormatTime());

                //Product Status
                productDetailsModel.setProductStatus(ProductStatus.UNDER_REVIEW.toString());

                //Set Product-Details To Product-Root Entity
                productRoot.setProductDetailsModels(List.of(productDetailsModel));

                //Set Product-Details to Product Size Rows
                for (ProductSizeRows productSizeRows : productDetailsModel.getProductSizeRows()) {
                    productSizeRows.setProductDetailsModel(productDetailsModel);
                    //Set UserId and UserName
                    productSizeRows.setUserId(String.valueOf(username.getId()));
                    productSizeRows.setUsername(String.valueOf(username.getUsername()));
                }

                //save Data Details
                ProductRoot productData = this.productRootRepo.save(productRoot);

                Map<Object, Object> mapNode = new HashMap<>();
                mapNode.put("id", productData.getProductDetailsModels().get(0).getId());
                return ResponseGenerator.generateSuccessResponse(mapNode, "Success");
            } else {
                return ResponseGenerator.generateBadRequestResponse();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }


    public VariantCategoryModel checkVariantId(long variantId) {
        try {
            VariantCategoryModel variantCategoryData = this.variantCategoryRepo.findById(variantId).orElseThrow(() -> new DataNotFoundException("Variant Category Id not found :: " + variantId));
            return variantCategoryData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<?> saveProductFiles(MultipartFile[] files, MultipartFile video, long productId) {
        try {
            ProductDetailsModel productDetails = this.productDetailsRepo.findById(productId)
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
                    log.info("Files Upload to Cloudinary Cloud End");

                    // Replace old images with new ones
                    log.info("SAVE FILE TO DB FLYING...");
                    productDetails.getProductFiles().clear();
                    productDetails.getProductFiles().addAll(productFilesList);
                    this.productDetailsRepo.save(productDetails);
                    log.info("========= ALL IMAGES SAVED SUCCESSFULLY =========");
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

            //Save ProductKey and Product to Binding Data
            this.saveProductKeysBinding(productDetails.getProductRoot());

            return ResponseGenerator.generateSuccessResponse("Files uploaded successfully for productId: " + productId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Something went wrong while saving product files");
        }
    }


    // ================= IMAGE HANDLING =================
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


    private User getUserDetails(String username) {
        try {
            return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found.."));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



//    Binding Variable ProductId,ProductKey,ProductDetailsId
    public void saveProductKeysBinding(ProductRoot productRoot) {
        try {
            log.info("Flying SAVE-PRODUCT-KEYS-BINDINGS...");
            long productId = productRoot.getId();
            String productKey = this.productServiceHelper.generateProductKey();

            //Set Product Key to Product Root
            productRoot.setProductKey(productKey);

            for (ProductDetailsModel pdm : productRoot.getProductDetailsModels()) {
                pdm.setProductId(productId);
                pdm.setProductKey(productKey);

                for (ProductFiles pf : pdm.getProductFiles()) {
                    pf.setProductId(productId);
                    pf.setProductKey(productKey);
                    pf.setProductDetailsId(pdm.getId());
                }

                for (ProductSizeRows psr : pdm.getProductSizeRows()){
                    psr.setProductId(productId);
                    psr.setProductKey(productKey);
                    psr.setProductDetailsId(pdm.getId());
                }
            }
            //save Product Root with Product-Id and OProduct Key Binds
            this.productRootRepo.save(productRoot);
        } catch (Exception e) {
            log.info("Exception generate in saveProductKeysBinding");
            e.getMessage();
            e.printStackTrace();
        }
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
