package com.coder.springjwt.controllers.customer.customerAuthController;

import com.coder.springjwt.constants.customerConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.dtos.customerPayloads.customerPayload.CustForgotPasswordPayload;
import com.coder.springjwt.dtos.customerPayloads.customerPayload.CustomerLoginPayload;
import com.coder.springjwt.dtos.customerPayloads.customerPayload.FreshSignUpPayload;
import com.coder.springjwt.dtos.customerPayloads.freshUserPayload.FreshUserPayload;
import com.coder.springjwt.dtos.customerPayloads.freshUserPayload.VerifyMobileOtpPayload;
import com.coder.springjwt.services.customerServices.customerAuthService.CustomerAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CustomerUrlMappings.CUSTOMER_BASE_URL)
public class CustomerAuthController {
    @Autowired
    private CustomerAuthService customerAuthService;

    @PostMapping(CustomerUrlMappings.CUSTOMER_SIGN_IN)
    public ResponseEntity<?> customerAuthenticateUser(@Validated @RequestBody CustomerLoginPayload customerLoginPayload) {
        return customerAuthService.customerAuthenticateUser(customerLoginPayload);
    }

    @PostMapping(CustomerUrlMappings.CUSTOMER_SIGN_UP)
    public ResponseEntity<?> CustomerSignUp(@Valid @RequestBody FreshUserPayload freshUserPayload , HttpServletRequest request) {
        return customerAuthService.CustomerSignUp(freshUserPayload , request);
    }

    @PostMapping(CustomerUrlMappings.VERIFY_FRESH_USER_MOBILE_OTP)
    public ResponseEntity<?> verifyFreshUserMobileOtp(@Validated @RequestBody VerifyMobileOtpPayload verifyMobileOtpPayload) {
       return customerAuthService.verifyFreshUserMobileOtp(verifyMobileOtpPayload);
    }

    @PostMapping(CustomerUrlMappings.CUSTOMER_SIGN_UP_COMPLETED)
    public ResponseEntity<?> customerSignUpCompleted(@Valid @RequestBody FreshSignUpPayload freshSignUpPayload) {

        return this.customerAuthService.customerSignUpCompleted(freshSignUpPayload);
    }

    @PostMapping(CustomerUrlMappings.CUSTOMER_FORGOT_PASSWORD)
    public ResponseEntity<?> customerForgotPassword(@Valid @RequestBody CustForgotPasswordPayload custForgotPasswordPayload) {

        return this.customerAuthService.customerForgotPassword(custForgotPasswordPayload);
    }

    @PostMapping(CustomerUrlMappings.CUSTOMER_FORGOT_PASSWORD_FINAL)
    public ResponseEntity<?> customerForgotPasswordFinal(@Valid @RequestBody CustForgotPasswordPayload custForgotPasswordPayload) {
        return this.customerAuthService.customerForgotPasswordFinal(custForgotPasswordPayload);
    }




}
