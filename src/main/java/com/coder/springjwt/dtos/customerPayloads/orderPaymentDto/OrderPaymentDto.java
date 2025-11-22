package com.coder.springjwt.dtos.customerPayloads.orderPaymentDto;

import com.coder.springjwt.dtos.customerPayloads.cartItemDtos.CartItemsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaymentDto {

    private String razorpay_order_id;
    private String razorpay_payment_id;
    private String razorpay_signature;

    private List<CartItemsDto> cartItems;
}
