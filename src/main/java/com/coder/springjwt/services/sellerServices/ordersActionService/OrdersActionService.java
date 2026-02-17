package com.coder.springjwt.services.sellerServices.ordersActionService;

import com.coder.springjwt.dtos.sellerPayloads.orders.OrderAcceptDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface OrdersActionService {

    ResponseEntity<?> orderAccept(OrderAcceptDto orderAcceptDto);

}
