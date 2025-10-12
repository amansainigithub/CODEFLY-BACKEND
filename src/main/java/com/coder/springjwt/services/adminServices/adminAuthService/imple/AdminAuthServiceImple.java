package com.coder.springjwt.services.adminServices.adminAuthService.imple;

import com.coder.springjwt.controllers.admin.adminAuthController.AdminAuthController;
import com.coder.springjwt.dtos.request.LoginRequest;
import com.coder.springjwt.dtos.request.Passkey;
import com.coder.springjwt.dtos.request.SignupRequest;
import com.coder.springjwt.dtos.response.JwtResponse;
import com.coder.springjwt.helpers.admin.GeneratePassKeyAdmin;
import com.coder.springjwt.helpers.generateRandomNumbers.GenerateOTP;
import com.coder.springjwt.models.ERole;
import com.coder.springjwt.models.Role;
import com.coder.springjwt.models.User;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.security.services.UserDetailsImpl;
import com.coder.springjwt.services.adminServices.adminAuthService.AdminAuthService;
import com.coder.springjwt.util.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AdminAuthServiceImple implements AdminAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?>
    adminAuthenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                            .map(item -> item.getAuthority())
                            .collect(Collectors.toList());


        //Role Authentication only ADMIN can work
        roles.stream().forEach(e->{
            if(!e.equals("ROLE_ADMIN")){
                throw new RuntimeException("Cannot Access Role Missing!Error ");
            }
        });

        //Generate Pass Key
        String passKey = GeneratePassKeyAdmin.generatePassKey(78);
        if(passKey == null || passKey.length() < 10){
            throw new RuntimeException("PassKey Error " + AdminAuthController.class.getName());
        }

        System.out.println("PASS KEY :: " + passKey );
        User user =  this.userRepository.findByUsername(userDetails.getUsername()).get();
        user.setPassKey(passKey);
        userRepository.save(user);

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Override
    public ResponseEntity<?> passKey(Passkey passkey) {
        //check Passkey is Empty or Not
        if(passkey.getPassKey().equals(null)  || passkey.getPassKey().equals("")
                || passkey.getPassKey().equals("undefined") ){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("PassKey is Empty", HttpStatus.BAD_GATEWAY));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(passkey.getUsername(), passkey.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        //Fetch Current User to DB
        User user = this.userRepository.findByUsername(userDetails.getUsername()).get();
        //Check Passkey is Authenticated or Not
        if(passkey.getPassKey().trim().equals(user.getPassKey())){
            System.out.println("Passkey matched success");
        }else{
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error ! Invalid Passkey",HttpStatus.BAD_REQUEST));
        }

        //because of you have not entered the old passkey
        // everyTime that's why we have generated a new Passkey
        user.setPassKey("");
        this.userRepository.save(user);

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Override
    public ResponseEntity<?> adminSignUp(SignupRequest signUpRequest) {
        Set<String> strRoles = signUpRequest.getRole();

        if(strRoles == null )
        {
            throw new RuntimeException("Role Not Found");
        }
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!",HttpStatus.BAD_REQUEST));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!", HttpStatus.BAD_REQUEST));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                "ROLE_ADMIN-" + GenerateOTP.generateOtpByAlpha(6) + "-" + signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        // Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Admin Role is not found."));
        roles.add(adminRole);

        user.setRoles(roles);

        //set Project Role
        user.setProjectRole(ERole.ROLE_ADMIN.toString());
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("ADMIN :: User registered successfully!",HttpStatus.CREATED));

    }
}
