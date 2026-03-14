package com.coder.springjwt.buckets.shiprocketBucket.shiprocketDtos.createOrder;

import lombok.Data;

@Data
public class CheckServiceAvailability {

    private String order_id;

    private String shipment_id;

    private String cod;
}
