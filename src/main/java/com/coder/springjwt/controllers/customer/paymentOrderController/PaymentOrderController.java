package com.coder.springjwt.controllers.customer.paymentOrderController;

import com.coder.springjwt.constants.customerConstants.customerUrlMappings.CustomerUrlMappings;
import com.coder.springjwt.dtos.customerPayloads.cartItemDtos.CartItemsDto;
import com.coder.springjwt.dtos.customerPayloads.orderPaymentDto.OrderPaymentDto;
import com.coder.springjwt.services.customerServices.orderPaymentService.OrderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(CustomerUrlMappings.PAYMENT_ORDER_CONTROLLER)
public class PaymentOrderController {

    @Autowired
    private OrderPaymentService orderPaymentService;


    @PostMapping(CustomerUrlMappings.CREATE_ORDER)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> createOrder(@RequestParam Double amount ,
                                         @RequestParam long addressId ,
                                         @RequestBody List<CartItemsDto> cartItems) {
        return this.orderPaymentService.createOrder(amount, addressId, cartItems);
    }


    @PostMapping(CustomerUrlMappings.ORDER_UPDATE)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> orderUpdate(@RequestBody OrderPaymentDto orderPaymentDto) {
        return this.orderPaymentService.orderUpdate(orderPaymentDto);
    }
}
