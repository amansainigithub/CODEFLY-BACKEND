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

    @PostMapping(SellerUrlMappings.GET_PENDING_ORDERS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getPendingOrders( @RequestParam Integer page ,
                                               @RequestParam  Integer size ,
                                               @RequestParam String username ) {

        return this.ordersService.getPendingOrders(page , size , username);
    }

    @PostMapping(SellerUrlMappings.GET_CONFIRMED_ORDERS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getConfirmedOrders( @RequestParam Integer page ,
                                                 @RequestParam  Integer size ,
                                                 @RequestParam String username ) {

        return this.ordersService.getConfirmedOrders(page , size , username);
    }

    @PostMapping(SellerUrlMappings.GET_SHIPPED_ORDERS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getShippedOrders( @RequestParam Integer page ,
                                                 @RequestParam  Integer size ,
                                                 @RequestParam String username ) {

        return this.ordersService.getShippedOrders(page , size , username);
    }

    @PostMapping(SellerUrlMappings.GET_DELIVERED_ORDERS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getDeliveredOrders( @RequestParam Integer page ,
                                               @RequestParam  Integer size ,
                                               @RequestParam String username ) {

        return this.ordersService.getDeliveredOrders(page , size , username);
    }


    @PostMapping(SellerUrlMappings.GET_CALCELLED_ORDERS)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getCancelledOrders( @RequestParam Integer page ,
                                                 @RequestParam  Integer size ,
                                                 @RequestParam String username ) {

        return this.ordersService.getCancelledOrders(page , size , username);
    }

}
