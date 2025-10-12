package com.coder.springjwt.services.sellerServices.sellerTaxService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerPayloads.sellerPayload.SellerTaxPayload;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.helpers.validateGstRegex.ValidateGstRegex;
import com.coder.springjwt.models.ERole;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.sellerModels.sellerTax.SellerTax;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.sellerRepository.sellerGstRepository.SellerTaxRepository;
import com.coder.springjwt.services.sellerServices.sellerTaxService.SellerTaxService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
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
public class SellerTaxServiceImple implements SellerTaxService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerTaxRepository sellerTaxRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserHelper userHelper;




    @Override
    public ResponseEntity<?> saveAndVerifyTaxDetails(SellerTaxPayload sellerTaxPayload) {
        MessageResponse response = new MessageResponse();
        try {

            //check GST Pattern
            if(ValidateGstRegex.isValidGstNumberRegex(sellerTaxPayload.getGstNumber()))
            {

                //First Validate User [username and password]
                if(this.userAuthenticated(sellerTaxPayload))
                {
                    log.info("User Authenticated Success");
                    if(isGstAnotherSeller(sellerTaxPayload , response))
                    {
                        log.info("Gst Number Used For Another Seller.....{}");
                        response.setMessage(SellerMessageResponse.GST_NUMBER_ALREADY_USED_ANOTHER_SELLER);
                        response.setStatus(HttpStatus.BAD_REQUEST);
                        return ResponseGenerator.generateBadRequestResponse(response,SellerMessageResponse.FAILED);
                    }else {

                        //second Validate User username and Registration Completed Flag (Y) AND ROLE-SELLER
                        Optional<User> sellerData = this.userRepository.findByUsernameAndSellerRegisterCompleteAndProjectRole
                                (sellerTaxPayload.getUsername()+SellerMessageResponse.SLR, "Y",
                                        ERole.ROLE_SELLER.toString());

                        //Seller is Present in the Database or seller is Valid
                        log.info("sellerData Present:: " + sellerData.isPresent());
                        if(sellerData.isPresent())
                        {
                            if (this.verifyGst(sellerTaxPayload.getGstNumber()))
                            {
                                log.info("Seller Data not Present in Database");
                                log.info("GST Verified Success");

                                //get UserName By UserHelper
                                Map<String, String> currentUser = userHelper.getCurrentUser();
                                User user = sellerData.get();

                                //set payload to seller Object
                                SellerTax sellerTax = new SellerTax();
                                //Set Current Username
                                sellerTax.setSellerUsername(currentUser.get("username") + " OR " + user.getUsername() );

                                sellerTax.setGstNumber(sellerTaxPayload.getGstNumber());
                                sellerTax.setSeller_key(toString().valueOf(Math.random()));
                                sellerTax.setSellerId(String.valueOf(sellerData.get().getId()));
                                sellerTax.setIsValidate("Y");

                                //save Seller Data
                                this.sellerTaxRepository.save(sellerTax);
                                log.info("Seller Tax Data Saved Success  {}" + sellerTaxPayload.getUsername());

                                response.setMessage(SellerMessageResponse.GST_VERIFIED);
                                response.setStatus(HttpStatus.OK);
                                return ResponseGenerator.generateSuccessResponse(response,SellerMessageResponse.SUCCESS);
                            }else{
                                log.info("Gst Number Not Verified");
                                response.setMessage(SellerMessageResponse.INVALID_GST_NUMBER);
                                response.setStatus(HttpStatus.BAD_GATEWAY);
                                return ResponseGenerator.generateBadRequestResponse(response,SellerMessageResponse.FAILED);
                            }
                        }

                    }
                }
                throw  new RuntimeException("SOMETHING WENT WRONG OR INVALID CREDENTIALS");
            }else{
                response.setMessage(SellerMessageResponse.INVALID_GST_NUMBER);
                response.setStatus(HttpStatus.BAD_REQUEST);
                return ResponseGenerator.generateBadRequestResponse(response,SellerMessageResponse.SOMETHING_WENT_WRONG);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(response,SellerMessageResponse.SOMETHING_WENT_WRONG_OR_INVALID_CRENDITIALS);
        }
    }


    public  boolean userAuthenticated(SellerTaxPayload sellerTaxPayload)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(sellerTaxPayload.getUsername() + SellerMessageResponse.SLR, sellerTaxPayload.getPassword()));

        if(authentication.isAuthenticated())
        {
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    public boolean isGstAnotherSeller(SellerTaxPayload sellerTaxPayload , MessageResponse response)
    {
        Boolean flag = Boolean.FALSE;

        Optional<SellerTax> byGstNumber = this.sellerTaxRepository.findByGstNumber(sellerTaxPayload.getGstNumber());

        if(byGstNumber.isPresent())
        {
            flag  = Boolean.TRUE;
        }
        return flag;
    }

    public boolean verifyGst(String gstNumber)
    {
        Boolean flag = Boolean.FALSE;
        if(gstNumber.equals("23AAACC1206D2ZN") || gstNumber.equals("06AAACC1206D2ZJ") || gstNumber.equals("32AAACC1206D2ZO") )
        {
            //Write logic To Validate The GST Number and save Request Response Table
            flag = Boolean.TRUE;
        }
        return flag;
    }


}
