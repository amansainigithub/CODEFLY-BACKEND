package com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos.shipment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pickup {

    private String warehouse_name;
    private String name;
    private String address;
    private String address_2;
    private String city;
    private String state;
    private Integer pincode;
    private Long phone;
}
