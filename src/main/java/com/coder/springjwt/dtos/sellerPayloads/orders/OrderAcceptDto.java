package com.coder.springjwt.dtos.sellerPayloads.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data
@Getter
@Setter
public class OrderAcceptDto {

    private Long id;

    private String orderNoPerItem;

    private String userId;

    private String username;

    private String productName;

}
