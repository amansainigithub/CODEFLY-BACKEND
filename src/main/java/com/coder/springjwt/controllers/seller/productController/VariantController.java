package com.coder.springjwt.controllers.seller.productController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerPayloads.productDetailPayloads.ProductDetailsDto;
import com.coder.springjwt.services.sellerServices.productVariantService.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_VARIANT_CONTROLLER)
public class VariantController {

    @Autowired
    private ProductVariantService productVariantService;


    @GetMapping(SellerUrlMappings.LOAD_PRODUCT_DETAILS)
    public ResponseEntity<?> loadProductDetails(@PathVariable long productId ) {
        return this.productVariantService.loadProductDetails(productId);
    }

    @PostMapping(SellerUrlMappings.SAVE_PRODUCT_VARIANT_DETAILS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> saveProductVariantDetails(@RequestBody ProductDetailsDto productDetailsDto ,
                                                @PathVariable long variantId , @PathVariable long productId  ) {
        return this.productVariantService.saveProductVariantDetails(productDetailsDto, variantId , productId);
    }


    @PostMapping(SellerUrlMappings.SAVE_PRODUCT_VARIANT_FILES)
    public ResponseEntity<?> saveProductVariantFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "video", required = false) MultipartFile video,
            @PathVariable long productId,
            @PathVariable long existingProductId) {
        return this.productVariantService.saveProductVariantFiles(files,video,productId , existingProductId);
    }
}
