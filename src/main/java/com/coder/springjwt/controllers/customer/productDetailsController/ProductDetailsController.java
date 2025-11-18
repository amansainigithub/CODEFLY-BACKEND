package com.coder.springjwt.controllers.customer.productDetailsController;

import com.coder.springjwt.constants.customerConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.services.homePageService.HomePageService;
import com.coder.springjwt.services.productDetailsServices.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CustomerUrlMappings.PRODUCT_DETAILS_CUSTOMER_CONTROLLER)
public class ProductDetailsController {

    @Autowired
    private ProductDetailsService productDetailsService;


    @GetMapping(CustomerUrlMappings.GET_PRODUCT_DETAILS)
    public ResponseEntity<?> getProductDetails(@PathVariable Long productId , @PathVariable String productName) {
        return this.productDetailsService.getProductDetails(productId , productName);
    }

}
