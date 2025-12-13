package com.coder.springjwt.services.sellerServices.ordersService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface OrdersService {

    ResponseEntity<?> getPendingOrders(Integer page, Integer size, String username);

    ResponseEntity<?> getConfirmedOrders(Integer page, Integer size, String username);

    ResponseEntity<?> getShippedOrders(Integer page, Integer size, String username);

    ResponseEntity<?> getDeliveredOrders(Integer page, Integer size, String username);

    ResponseEntity<?> getCancelledOrders(Integer page, Integer size, String username);
}
