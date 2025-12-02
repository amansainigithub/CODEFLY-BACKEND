package com.coder.springjwt.buckets.cashFreeBucket;

import lombok.Data;

@Data
public class OrderRequest {
    private double amount;
    private String email;
    private String phone;

}
