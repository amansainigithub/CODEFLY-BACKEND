package com.coder.springjwt.services.sellerServices.ordersService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface OrdersService {

    ResponseEntity<?> getActiveOrders(Integer page, Integer size, String username);
}
