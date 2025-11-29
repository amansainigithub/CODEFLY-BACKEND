package com.coder.springjwt.dtos.customerPayloads.orderItemsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsDto {

    private Long id;

    private String productId;
    private String productName;
    private String productPrice;
    private String productSize;
    private String quantity;
    private String totalPrice;
    private String fileUrl;
    private String productColor;
    private String productMrp;
    private String productDiscount;
    private String orderNoPerItem;
    private String productBrand;

    // Shipping address object
    private Long shippingAddressId;
    private String country;
    private String customerName;
    private String mobileNumber;
    private String area;
    private String postalCode;
    private String addressLine1;
    private String addressLine2;
    private boolean defaultAddress;
    private String city;
    private String state;
}
