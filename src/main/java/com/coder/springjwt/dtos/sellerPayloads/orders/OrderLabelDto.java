package com.coder.springjwt.dtos.sellerPayloads.orders;


import lombok.Data;

import java.util.List;

@Data
public class OrderLabelDto {

    List<String> shipmentIds;
}
