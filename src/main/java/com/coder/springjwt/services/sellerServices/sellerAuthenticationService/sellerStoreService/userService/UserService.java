package com.coder.springjwt.services.sellerServices.sellerAuthenticationService.sellerStoreService.userService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    public ResponseEntity<?> getCustomerByPagination(Integer page, Integer size);


    public ResponseEntity<?> getAdminByPagination(Integer page, Integer size);

    public ResponseEntity<?> getSellerByPagination(Integer page, Integer size);


}
