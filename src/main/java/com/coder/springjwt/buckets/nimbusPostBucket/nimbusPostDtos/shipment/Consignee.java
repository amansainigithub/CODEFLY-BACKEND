package com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos.shipment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consignee {

    private String name;              // optional
    private String address;           // required
    private String address_2;         // optional
    private String city;              // required
    private String state;             // required
    private Integer pincode;          // required
    private Long phone;
}
