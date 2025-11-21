package com.coder.springjwt.services.customerServices.customerAddressesService;

import com.coder.springjwt.dtos.customerPayloads.customerAddressDto.CustomerAddressDto;
import org.springframework.http.ResponseEntity;

public interface CustomerAddressService {
    ResponseEntity<?> saveAddress(CustomerAddressDto customerAddressDto);

    ResponseEntity<?> getAddress();

    ResponseEntity<?> deleteAddress(long id);

    ResponseEntity<?> setDefaultAddress(long id);

    ResponseEntity<?> getAddressById(long id);

    ResponseEntity<?> updateAddress(CustomerAddressDto customerAddressDto);
}
