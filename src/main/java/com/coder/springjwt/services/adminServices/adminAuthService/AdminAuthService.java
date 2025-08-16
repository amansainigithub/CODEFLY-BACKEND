package com.coder.springjwt.services.adminServices.adminAuthService;

import com.coder.springjwt.dtos.request.LoginRequest;
import com.coder.springjwt.dtos.request.Passkey;
import com.coder.springjwt.dtos.request.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface AdminAuthService {

    public ResponseEntity<?> adminAuthenticateUser(LoginRequest loginRequest);

    public ResponseEntity<?> passKey( Passkey passkey);

    public ResponseEntity<?> adminSignUp(SignupRequest signUpRequest);


}
