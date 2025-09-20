package com.coder.springjwt.controllers.seller.productController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerPayloads.productDetailPayloads.ProductDetailsDto;
import com.coder.springjwt.services.sellerServices.sellerProductService.ProductService;
import com.coder.springjwt.util.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_CONTROLLER)
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(SellerUrlMappings.SAVE_PRODUCT_DETAILS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveProductDetails(@RequestBody ProductDetailsDto productDetailsDto ,
                                                @PathVariable long variantId   ) {

        System.out.println("Product Details :: "+ productDetailsDto.toString());
        return this.productService.saveProductDetails(productDetailsDto, variantId);
    }


    @PostMapping(SellerUrlMappings.SAVE_PRODUCT_FILES)
    public ResponseEntity<?> uploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "video", required = false) MultipartFile video,
            @PathVariable long productId) {
        try {
            Thread.sleep(2000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        // Allowed image extensions
        List<String> allowedImageExtensions = Arrays.asList("jpg", "jpeg", "png");
        long maxImageSize = 1 * 1024 * 1024; // 1 MB

        int index = 1;
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                System.out.println("Slot " + index + " is empty");
                index++;
                continue; // skip this slot
            }

            String fileName = file.getOriginalFilename();
            if (fileName == null || !fileName.contains(".")) {
                return ResponseEntity.badRequest().body("Invalid file name at slot " + index);
            }

            // Extension check
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (!allowedImageExtensions.contains(ext)) {
                return ResponseEntity.badRequest()
                        .body("Invalid file type at slot " + index + " (" + fileName + "). Only JPG and PNG allowed.");
            }

            // Size check
            if (file.getSize() > maxImageSize) {
                return ResponseEntity.badRequest()
                        .body("File " + fileName + " at slot " + index + " exceeds the 1 MB size limit.");
            }

            // ✅ Save image file (example)
            System.out.println("✅ Image accepted at slot " + index + " :: " + fileName + " | Size: " + file.getSize());
            // file.transferTo(new File("uploads/images/" + fileName));

            index++;
        }

        //Video handling
        if (video != null && !video.isEmpty()) {
            String videoName = video.getOriginalFilename();
            if (videoName == null || !videoName.contains(".")) {
                return ResponseEntity.badRequest().body("Invalid video file name");
            }

            String videoExt = videoName.substring(videoName.lastIndexOf(".") + 1).toLowerCase();
            List<String> allowedVideoExtensions = Arrays.asList("mp4");

            long minVideoSize = 1 * 1024 * 1024;   // 1 MB
            long maxVideoSize = 50 * 1024 * 1024;  // 50 MB

            // Extension check
            if (!allowedVideoExtensions.contains(videoExt)) {
                return ResponseEntity.badRequest()
                        .body("Invalid video type (" + videoName + "). Only MP4 allowed.");
            }

            // Size check - Min 1 MB
            if (video.getSize() < minVideoSize) {
                return ResponseEntity.badRequest()
                        .body("Video " + videoName + " must be at least 1 MB in size.");
            }

            // Size check - Max 50 MB
            if (video.getSize() > maxVideoSize) {
                return ResponseEntity.badRequest()
                        .body("Video " + videoName + " exceeds the 50 MB size limit.");
            }

            // ✅ Save video file (example)
            System.out.println("✅ Video accepted :: " + videoName + " | Size: " + video.getSize());
            // video.transferTo(new File("uploads/videos/" + videoName));
        }
        return ResponseGenerator.generateSuccessResponse("SUCCESS");
    }



}
