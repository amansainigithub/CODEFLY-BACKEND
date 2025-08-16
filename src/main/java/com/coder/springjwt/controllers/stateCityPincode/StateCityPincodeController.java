package com.coder.springjwt.controllers.stateCityPincode;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.stateCityPincodeServices.StateCityPincodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SellerUrlMappings.STATE_CITY_PINCODE_CONTROLLER)
public class StateCityPincodeController {


    @Autowired
    private StateCityPincodeService stateCityPincodeService;


    @GetMapping(SellerUrlMappings.STATE_CITY_PINCODE)
    public ResponseEntity<?> stateCityPincode(@PathVariable int pincode) {
        return stateCityPincodeService.getCityStatePincode(pincode);
    }
}
