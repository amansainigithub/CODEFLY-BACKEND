package com.coder.springjwt.controllers.customer.customerAddressController;

import com.coder.springjwt.constants.customerConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.dtos.customerPayloads.customerAddressDto.CustomerAddressDto;
import com.coder.springjwt.services.customerServices.customerAddressesService.CustomerAddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CustomerUrlMappings.ADDRESS_CONTROLLER)
public class CustomerAddressController {

    @Autowired
    private CustomerAddressService customerAddressService;

    @PostMapping(CustomerUrlMappings.SAVE_ADDRESS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> saveAddress(@Valid @RequestBody CustomerAddressDto customerAddressDto) {
        return this.customerAddressService.saveAddress(customerAddressDto);
    }

    @GetMapping(CustomerUrlMappings.GET_ADDRESS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getAddress() {
        return this.customerAddressService.getAddress();
    }

    @GetMapping(CustomerUrlMappings.DELETE_ADDRESS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> deleteAddress(@PathVariable(required = true) long id) {
        return this.customerAddressService.deleteAddress(id);
    }


    @GetMapping(CustomerUrlMappings.SET_DEFAULT_ADDRESS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> setDefaultAddress(@PathVariable(required = true) long id) {
        return this.customerAddressService.setDefaultAddress(id);
    }

    @GetMapping(CustomerUrlMappings.GET_ADDRESS_BY_ID)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getAddressById(@PathVariable(required = true) long id) {
        return this.customerAddressService.getAddressById(id);
    }

    @PostMapping(CustomerUrlMappings.UPDATE_ADDRESS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> updateAddress(@Valid @RequestBody CustomerAddressDto customerAddressDto) {
        return this.customerAddressService.updateAddress(customerAddressDto);
    }

}
