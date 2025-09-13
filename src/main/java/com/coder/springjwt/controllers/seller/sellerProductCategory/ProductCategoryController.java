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
    public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        // Allowed extensions
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png");
        long maxSize = 1 * 1024 * 1024; // 1 MB

        int index = 1;
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                System.out.println("Slot " + index + " is empty");
                index++;
                continue; // skip this slot, move to next
            }

            String fileName = file.getOriginalFilename();
            if (fileName == null || !fileName.contains(".")) {
                return ResponseEntity.badRequest().body("Invalid file name at slot " + index);
            }

            // Extension check
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (!allowedExtensions.contains(ext)) {
                return ResponseEntity.badRequest()
                        .body("Invalid file type at slot " + index + " (" + fileName + "). Only JPG and PNG allowed.");
            }

            // Size check
            if (file.getSize() > maxSize) {
                return ResponseEntity.badRequest()
                        .body("File " + fileName + " at slot " + index + " exceeds the 5 MB size limit.");
            }

            // ✅ Yaha save karna hai (disk / DB me)
            System.out.println("File accepted at slot " + index + " :: " + fileName + " | Size: " + file.getSize());

            // Example:
            // file.transferTo(new File("uploads/" + fileName));

            index++;
        }

        return ResponseEntity.ok("Files processed successfully!");
    }



}
