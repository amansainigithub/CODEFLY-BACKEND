package com.coder.springjwt.buckets.razorpayBucket.razorpayDtos;

import lombok.Data;

@Data
public class RazorpayOrderDetailsDto {
    private String orderId;
    private Integer amount;
    private Integer amountPaid;
    private Integer amountDue;
    private String currency;
    private String status;
    private Integer attempts;
    private String createdAt;
}
