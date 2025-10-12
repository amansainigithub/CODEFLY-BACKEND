package com.coder.springjwt.controllers.seller.sellerStore;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerPayloads.sellerPayload.SellerStorePayload;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SellerUrlMappings.SELLER_STORE_CONTROLLER)
public class SellerStoreController {
    @Autowired
    SellerStoreService sellerStoreService;
    @PostMapping(SellerUrlMappings.SELLER_STORE)
    public ResponseEntity<?> sellerStore(@Validated @RequestBody SellerStorePayload sellerStorePayload) {
        return sellerStoreService.sellerStore(sellerStorePayload);
    }



}
