package com.coder.springjwt.controllers.seller.sellerBank;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerPayloads.sellerPayload.SellerBankPayload;
import com.coder.springjwt.services.sellerServices.sellerBankService.SellerBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(SellerUrlMappings.SELLER_BANK_CONTROLLER)
public class SellerBankController {

    @Autowired
    SellerBankService sellerBankService;


    @PostMapping(SellerUrlMappings.SELLER_BANK)
    public ResponseEntity<?> sellerBank(@Validated @RequestBody SellerBankPayload sellerBankPayload) {
        return sellerBankService.sellerBank(sellerBankPayload);
    }

}
