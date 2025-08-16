package com.coder.springjwt.services.customerServices.customerAuthService;

import com.coder.springjwt.dtos.customerPayloads.customerPayload.CustForgotPasswordPayload;
import com.coder.springjwt.dtos.customerPayloads.customerPayload.CustomerLoginPayload;
import com.coder.springjwt.dtos.customerPayloads.customerPayload.FreshSignUpPayload;
import com.coder.springjwt.dtos.customerPayloads.freshUserPayload.FreshUserPayload;
import com.coder.springjwt.dtos.customerPayloads.freshUserPayload.VerifyMobileOtpPayload;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface CustomerAuthService {

    public ResponseEntity<?> customerAuthenticateUser(CustomerLoginPayload customerLoginPayload);

    public ResponseEntity<?> CustomerSignUp(FreshUserPayload freshUserPayload , HttpServletRequest request);

    public ResponseEntity<?> verifyFreshUserMobileOtp(VerifyMobileOtpPayload verifyMobileOtpPayload);

    public  ResponseEntity<?> customerSignUpCompleted(FreshSignUpPayload freshSignUpPayload);

    public  ResponseEntity<?> customerForgotPassword(CustForgotPasswordPayload custForgotPasswordPayload);

    ResponseEntity<?> customerForgotPasswordFinal(CustForgotPasswordPayload custForgotPasswordPayload);
}
