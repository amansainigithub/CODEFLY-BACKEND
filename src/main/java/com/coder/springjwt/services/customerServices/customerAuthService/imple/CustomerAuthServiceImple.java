package com.coder.springjwt.services.customerServices.customerAuthService.imple;

import com.coder.springjwt.constants.OtpMessageContent;
import com.coder.springjwt.constants.customerConstants.messageConstants.test.CustMessageResponse;
import com.coder.springjwt.controllers.customer.customerAuthController.CustomerAuthController;
import com.coder.springjwt.exception.customerException.InvalidMobileNumberException;
import com.coder.springjwt.exception.customerException.InvalidUsernameAndPasswordException;
import com.coder.springjwt.helpers.OsLeaked.OsLeaked;
import com.coder.springjwt.helpers.ValidateMobNumber.ValidateMobileNumber;
import com.coder.springjwt.helpers.generateRandomNumbers.GenerateOTP;
import com.coder.springjwt.helpers.passwordValidation.PasswordValidator;
import com.coder.springjwt.models.ERole;
import com.coder.springjwt.models.Role;
import com.coder.springjwt.models.User;
import com.coder.springjwt.dtos.customerPayloads.customerPayload.CustForgotPasswordPayload;
import com.coder.springjwt.dtos.customerPayloads.customerPayload.CustomerLoginPayload;
import com.coder.springjwt.dtos.customerPayloads.customerPayload.FreshSignUpPayload;
import com.coder.springjwt.dtos.customerPayloads.freshUserPayload.FreshUserPayload;
import com.coder.springjwt.dtos.customerPayloads.freshUserPayload.VerifyMobileOtpPayload;
import com.coder.springjwt.dtos.response.JwtResponse;
import com.coder.springjwt.repository.RoleRepository;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.security.jwt.JwtUtils;
import com.coder.springjwt.security.services.UserDetailsImpl;
import com.coder.springjwt.services.MobileOtpService.MobileOtpService;
import com.coder.springjwt.services.customerServices.customerAuthService.CustomerAuthService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CustomerAuthServiceImple implements CustomerAuthService {

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

    @Autowired
    private MobileOtpService mobileOtpService;

    Logger logger  = LoggerFactory.getLogger(CustomerAuthController.class);

    @Override
    public ResponseEntity<?> customerAuthenticateUser(CustomerLoginPayload customerLoginPayload) {
        if(customerLoginPayload.getUserrole().equals("ROLE_CUSTOMER"))
        {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(customerLoginPayload.getUsername(), customerLoginPayload.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            for(String role : roles){
                if(role.equals("ROLE_CUSTOMER")){
                    return ResponseEntity.ok(new JwtResponse(jwt,
                            userDetails.getId(),
                            userDetails.getUsername(),
                            userDetails.getEmail(),
                            roles));
                }
            }

        } else{
            logger.error("CustomerAuthService :: " + "Unauthorized User ==> " + customerLoginPayload.getUsername() );
            throw new InvalidUsernameAndPasswordException(CustMessageResponse.INVALID_USERNAME_AND_PASSWORD);
        }
        return ResponseEntity.badRequest().body("Error: Unauthorized");
    }

    @Override
    public ResponseEntity<?> CustomerSignUp(FreshUserPayload freshUserPayload, HttpServletRequest request) {

        //Validate Mobile Number
        //FreshUserPayload Parameter Role Is Not Mandatory------
        if(!ValidateMobileNumber.isValid(freshUserPayload.getUsername())) {
            throw new InvalidMobileNumberException(CustMessageResponse.INVALID_MOBILE_NUMBER);
        }

        Optional<User> userOptional = userRepository.findByUsername(freshUserPayload.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(user.getCustomerRegisterComplete().equals("Y") && user.getCustomerMobileVerify().equals("Y"))
            {
                return ResponseEntity
                        .ok()
                        .body(new MessageResponse(CustMessageResponse.FLY_LOGIN_PAGE,HttpStatus.OK));
            }
            else if(user.getCustomerRegisterComplete().equals("N") && user.getCustomerMobileVerify().equals("N"))
            {
                this.userRepository.deleteById(user.getId());
                logger.info("deleted Success:: " + user.getId());

                logger.info("User save Process start");
                return this.saveUser(freshUserPayload , request);
            }
            else if(user.getCustomerRegisterComplete().equals("N") && user.getCustomerMobileVerify().equals("Y"))
            {
                this.userRepository.deleteById(user.getId());
                logger.info("Deleted Success :: when  Registration Complete 'Y' and mobile Verified 'N' :: " + user.getId());
                logger.info("User save Process start");
                return this.saveUser(freshUserPayload , request);
            }
            else{
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse(CustMessageResponse.USERNAME_ALREADY_TAKEN,HttpStatus.BAD_REQUEST));
            }
        } else if (userOptional.isEmpty()) {
            //save the users....
            return this.saveUser(freshUserPayload , request);
        }
        else
        {
            logger.error(CustMessageResponse.SOMETHING_WENT_WRONG);
            return ResponseEntity.badRequest().body(CustMessageResponse.SOMETHING_WENT_WRONG);
        }

    }


    public ResponseEntity<?> saveUser(FreshUserPayload freshUserPayload , HttpServletRequest request)
    {
        // Create new user's account and Random generated Male
        User user = new User(freshUserPayload.getUsername(),
                freshUserPayload.getUsername()+ "-ROLE_CUSTOMER-" + GenerateOTP.generateOtpByAlpha(6) +"-"+ "NO@gmail.com",
                encoder.encode("password"),
                freshUserPayload.getUsername());

        //Mobile OTP Generator [Mobile]
        String otp = GenerateOTP.generateOtp(6);
        logger.info("OTP SUCCESSFULLY GENERATED :: " + otp);

        //SEND OTP TO MOBILE
        try {
            mobileOtpService.sendSMS(freshUserPayload.getUsername(),
                    OtpMessageContent.getMessageContent(otp),"CUSTOMER" ,"MODE");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //Set Project Role
        user.setProjectRole(ERole.ROLE_CUSTOMER.toString());

        //User Set Username or mobile
        user.setMobile(freshUserPayload.getUsername());

        //Set Mobile OTP Verified
        user.setCustomerMobileVerify("N");
        //Set Reg Completed
        user.setCustomerRegisterComplete("N");

        user.setCustomerMobileOtp(otp);

        //Set<String> strRoles = customerSignUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                .orElseThrow(() -> new RuntimeException(CustMessageResponse.ROLE_CUSTOMER_NOT_FOUND));
        roles.add(customerRole);

        user.setRoles(roles);

        logger.info("=====Writing OS Data====");
        this.saveOsLeakedData(request , user);

        userRepository.save(user);
        logger.info(CustMessageResponse.FRESH_USER_CREATED_SUCCESSFULLY);

        return ResponseEntity.ok(new MessageResponse
                (CustMessageResponse.FRESH_USER_CREATED_SUCCESSFULLY,
                        HttpStatus.OK));
    }

    public void saveOsLeakedData(HttpServletRequest request, User user)
    {
        logger.info("Writing os data start");
        Map<String,String> node =OsLeaked.getOsData(request);
        user.setBrowserDetails(node.get("OsbrowserDetails"));
        user.setUserAgent(node.get("OsUserAgent"));
        user.setUserAgentVersion(node.get("osUser"));
        user.setOperatingSystem(node.get("operatingSystem"));
        user.setBrowserName(node.get("browserName"));
        logger.info("Writing os data Ending");
    }

    @Override
    public ResponseEntity<?> verifyFreshUserMobileOtp(VerifyMobileOtpPayload verifyMobileOtpPayload) {
        MessageResponse response = new MessageResponse();

        Optional<User> userOp =  this.userRepository.findByUsername(verifyMobileOtpPayload.getUsername());

        if(userOp.isEmpty())
        {
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.USERNAME_NOT_FOUND);
        }

        if(userOp.isPresent())
        {
            //Get User
            User user = userOp.get();

            if(user.getCustomerRegisterComplete().equals("Y") && user.getCustomerMobileVerify().equals("Y")){
            response.setMessage(CustMessageResponse.ALREADY_AUTHENTICATED);
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateBadRequestResponse(response);
            }

            if(verifyMobileOtpPayload.getMobileOtp().equals(user.getCustomerMobileOtp()))
            {
                //Verfied Users "Y"
                user.setCustomerMobileVerify("Y");

                // VERIFIED TRUE
                user.setCustomerMobileOtp("VERIFIED");
                this.userRepository.save(user);

                logger.info("OTP Verified Success");
                //Set Response Message
                response.setMessage("Verify OTP Success");
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(response,CustMessageResponse.OTP_VERIFIED_SUCCESS);
            }else{
                //Set Response Message
                response.setMessage("OTP Not Verified ");
                response.setStatus(HttpStatus.BAD_REQUEST);
                logger.error("OTP not Verified" , response);
                return ResponseEntity.badRequest().body(response);
            }
        }
        response.setMessage("Something Went Wrong");
        response.setStatus(HttpStatus.BAD_REQUEST);
        return ResponseGenerator.generateBadRequestResponse(response);
    }
    @Override
    public ResponseEntity<?> customerSignUpCompleted(FreshSignUpPayload freshSignUpPayload) {
                User user = userRepository.findByUsername(freshSignUpPayload.getUsername()).
                orElseThrow(()-> new RuntimeException("User Not Fount"));

        if ( (user.getCustomerRegisterComplete().equals("N")
                ||  user.getCustomerRegisterComplete().isEmpty()
                || user.getCustomerRegisterComplete() == null)
                && user.getCustomerMobileVerify().equals("Y")) {

            user.setCustomerRegisterComplete("Y");
            user.setPassword(encoder.encode(freshSignUpPayload.getPassword()));

            //Set Project Role
            user.setProjectRole(ERole.ROLE_CUSTOMER.toString());

            userRepository.save(user);
            logger.info("Registration Completed Fully");

            return ResponseEntity.ok(new MessageResponse(CustMessageResponse.REGISTER_COMPLETED_FULLY,HttpStatus.OK));
        }else{
            return ResponseEntity.badRequest().body(new MessageResponse(CustMessageResponse.SOMETHING_WENT_WRONG,HttpStatus.BAD_REQUEST));
        }
    }


    @Override
    public ResponseEntity<?> customerForgotPassword(CustForgotPasswordPayload custForgotPasswordPayload) {
        MessageResponse response = new MessageResponse();

        try {
           User user =  this.userRepository.findByUsernameAndCustomerRegisterComplete(custForgotPasswordPayload.getUsername() , "Y")
                    .orElseThrow( ()->  new UsernameNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

          if(!custForgotPasswordPayload.getPassword().equals(custForgotPasswordPayload.getConformPassword()))
          {
              logger.error(CustMessageResponse.PASSWORD_AND_CONFORM_PASSWORD_DOES_NOT_MATCH);
              response.setMessage(CustMessageResponse.PASSWORD_AND_CONFORM_PASSWORD_DOES_NOT_MATCH);
              response.setStatus(HttpStatus.BAD_REQUEST);
          }

          if(custForgotPasswordPayload.getPassword().equals(custForgotPasswordPayload.getConformPassword()))
          {
                logger.info("password Matched Success");
                boolean password =   PasswordValidator.validatePassword(custForgotPasswordPayload.getPassword());
                boolean conformPassword =   PasswordValidator.validatePassword(custForgotPasswordPayload.getConformPassword());

              if(user != null && password == Boolean.TRUE && conformPassword == Boolean.TRUE  ){
                logger.info("Password Matched Regex Success");

                  //Mobile OTP Generator [Mobile]
                  String otp = GenerateOTP.generateOtp(6);

                  logger.info(CustMessageResponse.FORGOT_PASSWORD_OTP_GENERATE_SUCCESS + " :: " + otp);

                  //SEND OTP TO MOBILE
                  try {
                      //Username == MobileNumber
                      mobileOtpService.sendSMS(custForgotPasswordPayload.getUsername(), OtpMessageContent.forgotPasswordOtpContent(otp),"CUSTOMER" ,"MODE");
                  }
                  catch (Exception e)
                  {
                      response.setMessage(CustMessageResponse.ERROR_THIRD_PARTY_API);
                      e.printStackTrace();
                  }
                  //Forgot Password Otp set to user Object
                  user.setCustomerForgotPasswordOtp(otp);
                  this.userRepository.save(user);
                  response.setMessage(CustMessageResponse.VALID_OTP_FORM_OPEN);
                  response.setStatus(HttpStatus.OK);
                  return ResponseEntity.ok(response);
              }else{
                response.setMessage(CustMessageResponse.PASSWORD_REGEX_ERROR);
                response.setStatus(HttpStatus.BAD_REQUEST);
                return ResponseEntity.badRequest().body(response);
              }
          }else{
              response.setMessage(CustMessageResponse.PASSWORD_AND_CONFORM_PASSWORD_DOES_NOT_MATCH);
              response.setStatus(HttpStatus.BAD_REQUEST);
              return ResponseEntity.badRequest().body(response);
          }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> customerForgotPasswordFinal(CustForgotPasswordPayload custForgotPasswordPayload) {
        MessageResponse response = new MessageResponse();
        try {
            System.out.println(custForgotPasswordPayload.toString());
            User user =  this.userRepository.findByUsernameAndCustomerRegisterComplete(custForgotPasswordPayload.getUsername() , "Y")
                    .orElseThrow( ()->  new UsernameNotFoundException("Username not Found"));

            if(custForgotPasswordPayload.getUsername() == null || custForgotPasswordPayload.getUsername() == ""
                || custForgotPasswordPayload.getPassword() == null || custForgotPasswordPayload.getPassword() == ""
                 || custForgotPasswordPayload.getOtp() == null || custForgotPasswordPayload.getOtp() == ""){

                logger.error("Something Went Wrong");
                return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
            }

            if(user!= null && user.getCustomerForgotPasswordOtp().trim().toString().equals(custForgotPasswordPayload.getOtp().trim().toString()))
            {
                    logger.info("Password Update Process Starting...");
                    user.setPassword(encoder.encode(custForgotPasswordPayload.getPassword()));

                    //set OTP TO null
                    user.setCustomerForgotPasswordOtp("");

                    //set OTP Update
                    user.setIsCustomerForgotPassword("Y");

                    userRepository.save(user);
                    response.setMessage("Password Update Success :: " + custForgotPasswordPayload.getUsername());
                    response.setStatus(HttpStatus.OK);
                    return ResponseEntity.ok(response);
            }else{
                response.setMessage("Username Not Found ! something went Wrong 111");
                response.setStatus(HttpStatus.BAD_REQUEST);
                return ResponseEntity.badRequest().body(response);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body(response);
    }





}
