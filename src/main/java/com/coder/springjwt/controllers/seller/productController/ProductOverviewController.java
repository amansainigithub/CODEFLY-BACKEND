package com.coder.springjwt.controllers.seller.productController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.sellerServices.productOverviewService.ProductOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SellerUrlMappings.PRODUCT_OVERVIEW_CONTROLLER)
public class ProductOverviewController {

    @Autowired
    private ProductOverviewService productOverviewService;


    @PostMapping(SellerUrlMappings.GET_UNDER_REVIEW_PRODUCT)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getUnderReviewProduct(@RequestParam Integer page ,
                                                   @RequestParam  Integer size ,
                                                   @RequestParam String username) {
        return this.productOverviewService.getUnderReviewProduct(page , size , username);
    }

    @PostMapping(SellerUrlMappings.GET_APPROVED_PRODUCT)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getApprovedProduct(@RequestParam Integer page ,
                                                   @RequestParam  Integer size ,
                                                   @RequestParam String username) {
        try {
            Thread.sleep(1000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return this.productOverviewService.getApprovedProduct(page , size , username);
    }



}
