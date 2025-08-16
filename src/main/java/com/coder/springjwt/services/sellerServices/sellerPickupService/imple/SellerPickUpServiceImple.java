package com.coder.springjwt.services.sellerServices.sellerPickupService.imple;


import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.ERole;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.sellerModels.sellerPickup.SellerPickup;
import com.coder.springjwt.dtos.sellerPayloads.sellerPayload.SellerPickUpPayload;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.sellerRepository.sellerBankRepository.SellerBankRepository;
import com.coder.springjwt.repository.sellerRepository.sellerGstRepository.SellerTaxRepository;
import com.coder.springjwt.repository.sellerRepository.sellerMobileRepository.SellerMobileRepository;
import com.coder.springjwt.repository.sellerRepository.sellerPickupRepository.SellerPickUpRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerStoreRepository;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.services.MobileOtpService.MobileOtpService;
import com.coder.springjwt.services.emailServices.EmailService.EmailService;
import com.coder.springjwt.services.sellerServices.sellerPickupService.SellerPickUpService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class SellerPickUpServiceImple implements SellerPickUpService {

    @Autowired
    private SellerMobileRepository sellerMobileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailService simpleEmailService;

    @Autowired
    private SellerTaxRepository sellerTaxRepository;

    @Autowired
    private MobileOtpService mobileOtpService;

    private static final long OTP_VALIDITY_DURATION = 1;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SellerPickUpRepository sellerPickUpRepository;

    @Autowired
    private SellerBankRepository sellerBankRepository;

    @Autowired
    private SellerStoreRepository sellerStoreRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> savePickUp(SellerPickUpPayload sellerPickUpPayload) {
        MessageResponse response = new MessageResponse();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(sellerPickUpPayload.getUsername()+":SLR", sellerPickUpPayload.getPassword()));

            if(authentication.isAuthenticated()) {

                //second Validate User username and Registration Completed Flag (Y) AND ROLE-SELLER
                Optional<User> sellerData = this.userRepository.findByUsernameAndSellerRegisterCompleteAndProjectRole
                        (sellerPickUpPayload.getUsername()+ SellerMessageResponse.SLR, "Y",
                                ERole.ROLE_SELLER.toString());

                //Seller is Present in the Database or seller is Valid
                log.info("sellerData Present:: " + sellerData.isPresent());
                if(sellerData.isPresent())
                {
                    Map<String, String> currentUser = UserHelper.getCurrentUser();
                    User user = sellerData.get();
                    //Save Pick Up data

                    SellerPickup sp = modelMapper.map(sellerPickUpPayload, SellerPickup.class);
                    sp.setUsername(currentUser.get("username") + " OR " + user.getUsername());
                    sp.setCountry("india");

                    this.sellerPickUpRepository.save(sp);

                    response.setMessage("pickUp Saved Success");
                    response.setStatus(HttpStatus.OK);
                    return ResponseGenerator.generateSuccessResponse(response,SellerMessageResponse.SUCCESS);

                }
            }
            throw  new RuntimeException("SOMETHING WENT WRONG OR INVALID CREDENTIALS");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setMessage("pickUp Saved Failed");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response,SellerMessageResponse.SOMETHING_WENT_WRONG);
        }
    }}
