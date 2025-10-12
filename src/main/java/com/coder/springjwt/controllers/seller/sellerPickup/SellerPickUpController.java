package com.coder.springjwt.controllers.seller.sellerPickup;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerPayloads.sellerPayload.SellerPickUpPayload;
import com.coder.springjwt.services.sellerServices.sellerPickupService.SellerPickUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SellerUrlMappings.SELLER_PICKUP_CONTROLLER)
public class SellerPickUpController {
    @Autowired
    SellerPickUpService sellerPickUpService;

    @PostMapping(SellerUrlMappings.SELLER_PICKUP)
    public ResponseEntity<?> sellerPickup(@Validated @RequestBody SellerPickUpPayload sellerPickUpPayload) {
        return sellerPickUpService.savePickUp(sellerPickUpPayload);
    }

}
