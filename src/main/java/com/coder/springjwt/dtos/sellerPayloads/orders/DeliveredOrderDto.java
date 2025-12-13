package com.coder.springjwt.dtos.sellerPayloads.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveredOrderDto {

    private Long id;
    private String productId;
    private String productName;
    private String productPrice;
    private String productBrand;
    private String productSize;
    private String quantity;
    private String totalPrice;
    private String fileUrl;
    private String productColor;
    private String productMrp;
    private String productDiscount;
    private String orderId;
    private String paymentState;
    private String userId;
    private String username;
    private String paymentMode;
    private String orderReferenceNo;
    private String orderNoPerItem;
    private String sellerId;
    private String sellerUsername;
    private Long addressId;
    private String country;
    private String customerName;
    private String mobileNumber;
    private String area;
    private String postalCode;
    private String addressLine1;
    private String addressLine2;
    private Boolean defaultAddress;
    private String city;
    private String state;

    private String orderStatus;

    private String orderDate;

    private String orderTime;
}
