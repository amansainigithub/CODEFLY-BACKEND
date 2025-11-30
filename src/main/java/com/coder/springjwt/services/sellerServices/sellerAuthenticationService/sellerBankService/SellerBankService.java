package com.coder.springjwt.services.sellerServices.sellerAuthenticationService.sellerBankService;

import com.coder.springjwt.dtos.sellerPayloads.sellerPayload.SellerBankPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerBankService {

    ResponseEntity<?> sellerBank(SellerBankPayload sellerBankPayload);

}
