package com.coder.springjwt.controllers.customer.customerOrdersController;

import com.coder.springjwt.constants.customerConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.services.customerServices.customerOrderService.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CustomerUrlMappings.CUSTOMER_ORDERS_CONTROLLER)
public class CustomerOrdersController {

    @Autowired
    private CustomerOrderService customerOrderService;

    @PostMapping(CustomerUrlMappings.FETCH_CUSTOMER_ORDERS)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> fetchCustomerOrders(@RequestParam Integer page ,
                                                 @RequestParam  Integer size,
                                                 @RequestParam String username) {
        return this.customerOrderService.fetchCustomerOrders(page , size , username);
    }

}
