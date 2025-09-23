package com.coder.springjwt.controllers.seller.productController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerPayloads.productDetailPayloads.ProductDetailsDto;
import com.coder.springjwt.services.sellerServices.sellerProductService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_CONTROLLER)
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(SellerUrlMappings.SAVE_PRODUCT_DETAILS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveProductDetails(@RequestBody ProductDetailsDto productDetailsDto ,
                                                @PathVariable long variantId   ) {

        return this.productService.saveProductDetails(productDetailsDto, variantId);
    }


    @PostMapping(SellerUrlMappings.SAVE_PRODUCT_FILES)
    public ResponseEntity<?> uploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "video", required = false) MultipartFile video,
            @PathVariable long productId) {
            return this.productService.saveProductFiles(files,video,productId);
        }


}
