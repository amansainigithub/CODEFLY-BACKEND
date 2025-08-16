package com.coder.springjwt.services.sellerServices.sellerStoreService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.ERole;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.sellerModels.sellerStore.SellerStore;
import com.coder.springjwt.dtos.sellerPayloads.sellerPayload.SellerStorePayload;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerStoreRepository;
import com.coder.springjwt.services.sellerServices.sellerStoreService.SellerStoreService;
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
public class SellerStoreServiceImple implements SellerStoreService {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SellerStoreRepository sellerStoreRepository;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public ResponseEntity<?> sellerStore(SellerStorePayload sellerStorePayload) {
        MessageResponse response = new MessageResponse();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(sellerStorePayload.getUsername()+":SLR", sellerStorePayload.getPassword()));

            if(authentication.isAuthenticated()) {

                //second Validate User username and Registration Completed Flag (Y) AND ROLE-SELLER
                Optional<User> sellerData = this.userRepository.findByUsernameAndSellerRegisterCompleteAndProjectRole
                        (sellerStorePayload.getUsername()+ SellerMessageResponse.SLR, "Y",
                                ERole.ROLE_SELLER.toString());

                //Seller is Present in the Database or seller is Valid
                log.info("sellerData Present:: " + sellerData.isPresent());
                if(sellerData.isPresent())
                {

                    //get UserName By UserHelper
                    Map<String, String> currentUser = UserHelper.getCurrentUser();
                    User user = sellerData.get();

                    SellerStore sellerStore = modelMapper.map(sellerStorePayload, SellerStore.class);
                    sellerStore.setUsername(user.getUsername());

                    //Set First Time UserName
                    sellerStore.setFt_username(currentUser.get("username") + " OR " + user.getUsername() );

                    //save Seller Store
                    this.sellerStoreRepository.save(sellerStore);

                    //Set Store Name to User Table
                    user.setSellerStoreName(sellerStore.getStoreName());
                    //save User
                    this.userRepository.save(user);

                    response.setMessage("Seller Store Saved Success");
                    response.setStatus(HttpStatus.OK);
                    return ResponseGenerator.generateSuccessResponse(response,SellerMessageResponse.SUCCESS);

                }
            }
            throw  new RuntimeException("SOMETHING WENT WRONG OR INVALID CREDENTIALS");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setMessage("Seller Store Not Saved");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response,SellerMessageResponse.SOMETHING_WENT_WRONG);
        }
    }


}
