package com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NimbusServiceabilityRequest {

    private String origin;
    private String destination;
    private String payment_type;
    private String order_amount;
    private String weight;
    private String length;
    private String breadth;
    private String height;
}
