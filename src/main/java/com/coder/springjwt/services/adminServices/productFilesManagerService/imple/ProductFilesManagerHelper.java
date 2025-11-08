package com.coder.springjwt.services.adminServices.productFilesManagerService.imple;

import com.coder.springjwt.buckets.filesBucket.bucketModels.BucketModel;
import com.coder.springjwt.buckets.filesBucket.bucketService.BucketService;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductFiles;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductFilesRepo;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Component
public class ProductFilesManagerHelper {

    @Autowired
    private ProductFilesRepo productFilesRepo;

    @Autowired
    private BucketService bucketService;

    @Autowired
    private ProductDetailsRepo productDetailsRepo;

    public ResponseEntity<?> updateExistingFile(MultipartFile file, String productFileId) {
        log.info("Updating existing product file: {}", productFileId);

        ProductFiles productFile = productFilesRepo.findById(Long.parseLong(productFileId))
                .orElseThrow(() -> new DataNotFoundException("Product File not found."));

        String contentType = file.getContentType();

        if (contentType == null) {
            return ResponseGenerator.generateBadRequestResponse("Invalid file type.");
        }

        if (contentType.startsWith("image/")) {
            return updateImageFile(file, productFile);
        } else if (contentType.startsWith("video/")) {
            return updateVideoFile(file, productFile);
        } else {
            return ResponseGenerator.generateBadRequestResponse("Invalid file type. Only image/video allowed.");
        }
    }

    private ResponseEntity<?> updateImageFile(MultipartFile file, ProductFiles productFile) {
        log.info("Updating existing image...");

        ResponseEntity<?> imageValidation = checkImageValidation(file);
        if (imageValidation != null) {
            return imageValidation;
        }

        BucketModel bucketModel = bucketService.uploadCloudinaryFile(file, "IMAGE");

        productFile.setFileType("IMAGE");
        productFile.setFileUrl(bucketModel.getBucketUrl());
        productFile.setFileName(bucketModel.getFileName());
        productFile.setFileSize(file.getSize());
        productFile.setContentType(file.getContentType());

        productFilesRepo.save(productFile);
        log.info("Product IMAGE updated successfully");

        return ResponseGenerator.generateSuccessResponse("Product image updated successfully.");
    }


    private ResponseEntity<?> updateVideoFile(MultipartFile file, ProductFiles productFile) {
        log.info("Updating existing video...");

        ResponseEntity<?> videoValidation = checkIsVideoValid(file);
        if (videoValidation != null) {
            return videoValidation;
        }

        BucketModel bucketModel = bucketService.uploadCloudinaryFile(file, "VIDEO");

        productFile.setFileType("VIDEO");
        productFile.setFileUrl(bucketModel.getBucketUrl());
        productFile.setFileName(bucketModel.getFileName());
        productFile.setFileSize(file.getSize());
        productFile.setContentType(file.getContentType());

        productFilesRepo.save(productFile);
        log.info("Product VIDEO updated successfully");

        return ResponseGenerator.generateSuccessResponse("Product video updated successfully.");
    }


    public ResponseEntity<?> addNewFile(MultipartFile file, String productId) {
        log.info("Adding new file for productId: {}", productId);

        ProductDetailsModel productDetails = productDetailsRepo.findById(Long.parseLong(productId))
                .orElseThrow(() -> new DataNotFoundException("Product not found: " + productId));

        String contentType = file.getContentType();

        if (contentType == null) {
            return ResponseGenerator.generateBadRequestResponse("Invalid file type.");
        }

        if (contentType.startsWith("image/")) {
            return addNewImageFile(file, productDetails);
        } else if (contentType.startsWith("video/")) {
            return addNewVideoFile(file, productDetails);
        } else {
            return ResponseGenerator.generateBadRequestResponse("Invalid file type. Only image/video allowed.");
        }
    }

    private ResponseEntity<?> addNewImageFile(MultipartFile file, ProductDetailsModel productDetails) {
        log.info("Adding new IMAGE...");

        ResponseEntity<?> imageValidation = checkImageValidation(file);
        if (imageValidation != null) {
            return imageValidation;
        }

        BucketModel bucketModel = bucketService.uploadCloudinaryFile(file, "IMAGE");

        ProductFiles newFile = new ProductFiles();
        newFile.setFileSize(file.getSize());
        newFile.setContentType(file.getContentType());
        newFile.setFileType("IMAGE");
        newFile.setFileUrl(bucketModel.getBucketUrl());
        newFile.setFileName(bucketModel.getFileName());
        newFile.setProductDetailsId(productDetails.getId());
        newFile.setProductKey(productDetails.getProductKey());
        newFile.setProductRootId(productDetails.getProductRoot().getId());
        newFile.setProductDetailsModel(productDetails);

        productFilesRepo.save(newFile);
        log.info("New product IMAGE added successfully");

        return ResponseGenerator.generateSuccessResponse("New product image added successfully.");
    }


    private ResponseEntity<?> addNewVideoFile(MultipartFile file, ProductDetailsModel productDetails) {
        log.info("Adding new VIDEO...");

        ResponseEntity<?> videoValidation = checkIsVideoValid(file);
        if (videoValidation != null) {
            return videoValidation;
        }

        BucketModel bucketModel = bucketService.uploadCloudinaryFile(file, "VIDEO");

        ProductFiles newFile = new ProductFiles();
        newFile.setFileSize(file.getSize());
        newFile.setContentType(file.getContentType());
        newFile.setFileType("VIDEO");
        newFile.setFileUrl(bucketModel.getBucketUrl());
        newFile.setFileName(bucketModel.getFileName());
        newFile.setProductDetailsId(productDetails.getId());
        newFile.setProductKey(productDetails.getProductKey());
        newFile.setProductRootId(productDetails.getProductRoot().getId());
        newFile.setProductDetailsModel(productDetails);

        productFilesRepo.save(newFile);
        log.info("New product VIDEO added successfully");

        return ResponseGenerator.generateSuccessResponse("New product video added successfully.");
    }




    // ================= IMAGE HANDLING =================
    public ResponseEntity<?> checkImageValidation(MultipartFile files) {
        List<String> allowedImageExtensions = Arrays.asList("jpg", "jpeg", "png");
        long maxImageSize = 5 * 1024 * 1024; // 5 MB

        String fileName = files.getOriginalFilename();
        if (fileName == null || !fileName.contains(".")) {
            return ResponseEntity.badRequest().body("Invalid file "+ fileName + "name at slot ");
        }

        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if (!allowedImageExtensions.contains(ext)) {
            return ResponseEntity.badRequest().body("Invalid file type at slot (" + fileName + "). Only JPG and PNG allowed.");
        }

        if (files.getSize() > maxImageSize) {
            return ResponseEntity.badRequest().body("File " + fileName + " at slot  exceeds the 1 MB size limit.");
        }
        return null; // null means SUCCESS
    }



    //VIDEO VALIDATION
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

}
