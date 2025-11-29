package com.coder.springjwt.buckets.razorpayBucket.razorpayDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RazorpaySignatureDto {

    private String razorpay_order_id;

    private String razorpay_payment_id;

    private String razorpay_signature;

}
