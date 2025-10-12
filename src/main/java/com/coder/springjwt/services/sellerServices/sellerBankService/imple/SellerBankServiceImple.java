package com.coder.springjwt.services.sellerServices.sellerBankService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerPayloads.sellerPayload.SellerBankPayload;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.ERole;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.sellerModels.sellerBank.SellerBank;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.sellerRepository.sellerBankRepository.SellerBankRepository;
import com.coder.springjwt.services.sellerServices.sellerBankService.SellerBankService;
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
public class SellerBankServiceImple implements SellerBankService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SellerBankRepository sellerBankRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserHelper userHelper;


    @Override
    public ResponseEntity<?> sellerBank(SellerBankPayload sellerBankPayload) {
        MessageResponse response = new MessageResponse();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(sellerBankPayload.getUsername() + SellerMessageResponse.SLR, sellerBankPayload.getPassword()));

            if(authentication.isAuthenticated()) {

                //second Validate User username and Registration Completed Flag (Y) AND ROLE-SELLER
                Optional<User> sellerData = this.userRepository.findByUsernameAndSellerRegisterCompleteAndProjectRole
                        (sellerBankPayload.getUsername()+ SellerMessageResponse.SLR, "Y",
                                ERole.ROLE_SELLER.toString());

                //Seller is Present in the Database or seller is Valid
                log.info("sellerData Present:: " + sellerData.isPresent());
                if(sellerData.isPresent())
                {

                    //Save Pick Up data
                    User user = sellerData.get();
                    Map<String, String> currentUser = userHelper.getCurrentUser();

                    SellerBank sellerBank = modelMapper.map(sellerBankPayload, SellerBank.class);
                    sellerBank.setUsername(currentUser.get("username") + " OR " + user.getUsername() );

                    this.sellerBankRepository.save(sellerBank);

                    response.setMessage("Bank Details Saved Success");
                    response.setStatus(HttpStatus.OK);
                    return ResponseGenerator.generateSuccessResponse(response,SellerMessageResponse.SUCCESS);

                }
            }
            throw  new RuntimeException("SOMETHING WENT WRONG OR INVALID CREDENTIALS");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setMessage("Bank Details Not Failed");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response,SellerMessageResponse.SOMETHING_WENT_WRONG);
        }
    }


}
