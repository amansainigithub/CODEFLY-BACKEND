package com.coder.springjwt.services.sellerServices.sellerStoreService;

import com.coder.springjwt.dtos.sellerPayloads.sellerPayload.SellerStorePayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerStoreService {

    ResponseEntity<?> sellerStore(SellerStorePayload sellerStorePayload);


}
