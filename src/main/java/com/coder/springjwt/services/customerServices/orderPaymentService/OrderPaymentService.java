package com.coder.springjwt.services.customerServices.orderPaymentService;

import com.coder.springjwt.dtos.customerPayloads.cartItemDtos.CartItemsDto;
import com.coder.springjwt.dtos.customerPayloads.orderPaymentDto.OrderPaymentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderPaymentService {
    ResponseEntity<?> createOrder(Double amount, long addressId, List<CartItemsDto> cartItems);

    ResponseEntity<?> orderUpdate(OrderPaymentDto orderPaymentDto);
}
