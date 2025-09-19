package com.coder.springjwt.controllers.seller.sellerProductCategory;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.sellerServices.productCategoryService.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_CATEGORY_CONTROLLER)
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping(SellerUrlMappings.GET_ROOT_CATEGORY)
    public ResponseEntity<?> getRootCategory() {
        return this.productCategoryService.getRootCategory();
    }

    @GetMapping(SellerUrlMappings.GET_SUB_CATEGORY)
    public ResponseEntity<?> getSubCategory(@PathVariable long id ) {
        return this.productCategoryService.getSubCategory(id);
    }

    @GetMapping(SellerUrlMappings.GET_TYPE_CATEGORY)
    public ResponseEntity<?> getTypeCategory(@PathVariable long id ) {
        return this.productCategoryService.getTypeCategory(id);
    }

    @GetMapping(SellerUrlMappings.GET_VARIANT_CATEGORY)
    public ResponseEntity<?> getVariantCategory(@PathVariable long id ) {
        return this.productCategoryService.getVariantCategory(id);
    }

    @PostMapping("/uploadFiles")
    public ResponseEntity<?> uploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "video", required = false) MultipartFile video) {

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
        return ResponseEntity.ok("Files processed successfully!");
    }




}
