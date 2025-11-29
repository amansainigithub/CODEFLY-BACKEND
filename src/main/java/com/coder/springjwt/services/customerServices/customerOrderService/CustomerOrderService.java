package com.coder.springjwt.services.customerServices.customerOrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface CustomerOrderService {
    ResponseEntity<?> fetchCustomerOrders(Integer page, Integer size , String username);
}
