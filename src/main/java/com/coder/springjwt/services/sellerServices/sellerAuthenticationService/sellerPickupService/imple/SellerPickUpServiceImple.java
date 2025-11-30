package com.coder.springjwt.services.sellerServices.sellerAuthenticationService.sellerPickupService.imple;


import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerPayloads.sellerPayload.SellerPickUpPayload;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.ERole;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.sellerModels.sellerPickup.SellerPickup;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.sellerRepository.sellerPickupRepository.SellerPickUpRepository;
import com.coder.springjwt.services.sellerServices.sellerAuthenticationService.sellerPickupService.SellerPickUpService;
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
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class SellerPickUpServiceImple implements SellerPickUpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SellerPickUpRepository sellerPickUpRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserHelper userHelper;

    @Override
    public ResponseEntity<?> savePickUp(SellerPickUpPayload sellerPickUpPayload) {
        MessageResponse response = new MessageResponse();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(sellerPickUpPayload.getUsername()+ SellerMessageResponse.SLR, sellerPickUpPayload.getPassword()));

            if(authentication.isAuthenticated()) {

                //second Validate User username and Registration Completed Flag (Y) AND ROLE-SELLER
                Optional<User> sellerData = this.userRepository.findByUsernameAndSellerRegisterCompleteAndProjectRole
                        (sellerPickUpPayload.getUsername()+ SellerMessageResponse.SLR, "Y",
                                ERole.ROLE_SELLER.toString());

                //Seller is Present in the Database or seller is Valid
                log.info("sellerData Present:: " + sellerData.isPresent());
                if(sellerData.isPresent())
                {
                    Map<String, String> currentUser = userHelper.getCurrentUser();
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
