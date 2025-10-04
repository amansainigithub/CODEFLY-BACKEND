package com.coder.springjwt.controllers.admin.productManagerController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerPayloads.productDetailPayloads.ProductDetailsDto;
import com.coder.springjwt.services.adminServices.productManagerService.ProductManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_MANAGER_CONTROLLER)
public class ProductManagerController {

    @Autowired
    private ProductManageService productManageService;


    @GetMapping(AdminUrlMappings.RETRIEVE_PRODUCT_DETAILS)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> retrieveProductDetails(@PathVariable long productId ) {
        return this.productManageService.retrieveProductDetails(productId);
    }


    @PostMapping(AdminUrlMappings.UPDATE_PRODUCT_DETAILS)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProductDetails(@RequestBody ProductDetailsDto productDetailsDto ,@PathVariable long productId  ) {
        System.out.println("productId ID :: " + productId);
        return this.productManageService.updateProductDetails(productDetailsDto , productId);
    }

}
