package com.coder.springjwt.controllers.seller.ordersController;


import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerPayloads.orders.OrderAcceptDto;
import com.coder.springjwt.services.sellerServices.ordersActionService.OrdersActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SellerUrlMappings.ORDERS_ACTION_CONTROLLER)
public class OrdersActionController {


    @Autowired
    private OrdersActionService ordersActionService;


    @PostMapping(SellerUrlMappings.ORDER_ACCEPT)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> orderAccept(@RequestBody OrderAcceptDto orderAcceptDto) {

        return this.ordersActionService.orderAccept(orderAcceptDto);
    }
}
