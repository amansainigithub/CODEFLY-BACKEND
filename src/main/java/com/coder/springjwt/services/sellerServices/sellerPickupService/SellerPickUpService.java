package com.coder.springjwt.services.sellerServices.sellerPickupService;

import com.coder.springjwt.dtos.sellerPayloads.sellerPayload.SellerPickUpPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerPickUpService {

    public ResponseEntity<?> savePickUp(SellerPickUpPayload sellerPickUpPayload);
}
