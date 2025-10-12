package com.coder.springjwt.controllers.admin.adminAuthController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.request.LoginRequest;
import com.coder.springjwt.dtos.request.Passkey;
import com.coder.springjwt.dtos.request.SignupRequest;
import com.coder.springjwt.services.adminServices.adminAuthService.AdminAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(AdminUrlMappings.ADMIN_AUTH_CONTROLLER)
public class AdminAuthController {
    @Autowired
    private AdminAuthService adminAuthService;


    @PostMapping(AdminUrlMappings.ADMIN_SIGN_IN)
    public ResponseEntity<?> adminAuthenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
      return adminAuthService.adminAuthenticateUser(loginRequest);
    }

    @PostMapping(AdminUrlMappings.ADMIN_PASS_KEY)
    public ResponseEntity<?> passKey(@Validated @RequestBody Passkey passkey) {
        return adminAuthService.passKey(passkey);
    }

    @PostMapping(AdminUrlMappings.ADMIN_SIGN_UP)
    public ResponseEntity<?> adminSignUp(@Valid @RequestBody SignupRequest signUpRequest) {
        return adminAuthService.adminSignUp(signUpRequest);
    }


}
