package com.coder.springjwt.services.sellerServices.sellerAuthenticationService.sellerAuthService;

import com.coder.springjwt.dtos.sellerPayloads.sellerPayload.SellerLoginPayload;
import com.coder.springjwt.dtos.sellerPayloads.sellerPayload.SellerMobilePayload;
import com.coder.springjwt.models.sellerModels.SellerMobile.SellerOtpRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface SellerAuthService {


    public ResponseEntity<?> sellerMobile(SellerMobilePayload sellerMobilePayload);

    public ResponseEntity<?> validateSellerOtp(SellerOtpRequest sellerOtpRequest);

    public ResponseEntity<?> sellerSignUp(SellerLoginPayload sellerLoginPayload ,  HttpServletRequest request);
}
