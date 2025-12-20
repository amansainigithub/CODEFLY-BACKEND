package com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos.shipment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private String name;
    private String qty;
    private String price;
    private String sku;
}
