package com.coder.springjwt.controllers.seller.ordersController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.sellerServices.ordersService.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SellerUrlMappings.ORDERS_CONTROLLER)
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping(SellerUrlMappings.GET_ACTIVE_ORDERS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getActiveOrders( @RequestParam Integer page ,
                                        @RequestParam  Integer size ,
                                        @RequestParam String username ) {

        return this.ordersService.getActiveOrders(page , size , username);
    }

}
