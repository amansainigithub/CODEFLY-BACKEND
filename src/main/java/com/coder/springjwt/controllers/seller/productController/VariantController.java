package com.coder.springjwt.controllers.seller.productController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.sellerServices.productVariantService.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_VARIANT_CONTROLLER)
public class VariantController {

    @Autowired
    private ProductVariantService productVariantService;


    @GetMapping(SellerUrlMappings.LOAD_PRODUCT_DETAILS)
    public ResponseEntity<?> loadProductDetails(@PathVariable long productId ) {
        return this.productVariantService.loadProductDetails(productId);
    }
}
