package com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostDtos.shipment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NimbusPostShipmentRequest {

    private String order_number;
    private Integer shipping_charges;
    private Integer discount;
    private Integer cod_charges;
    private String payment_type;
    private Integer order_amount;

    private Integer package_weight;
    private Integer package_length;
    private Integer package_breadth;
    private Integer package_height;

    private Consignee consignee;
    private Pickup pickup;
    private List<OrderItem> order_items;

}
