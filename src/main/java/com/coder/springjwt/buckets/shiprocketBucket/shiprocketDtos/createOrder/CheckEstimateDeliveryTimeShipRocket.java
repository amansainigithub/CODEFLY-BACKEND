package com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.createOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckEstimateDeliveryTimeShipRocket {

    private String pickup_postcode;

    private String delivery_postcode;

    private boolean cod;

    private String weight;

    private Integer qc_check;
}
