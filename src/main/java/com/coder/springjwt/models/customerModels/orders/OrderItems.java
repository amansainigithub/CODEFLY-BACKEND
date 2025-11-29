package com.coder.springjwt.models.customerModels.orders;

import com.coder.springjwt.models.customerModels.paymentsModels.PaymentOrders;
import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OrderItems")
public class OrderItems extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private String razorpayOrderId;

    private String paymentState;

    private String userId;

    private String username;

    private String paymentMode;

    private String orderReferenceNo;

    private String orderNoPerItem;


    //Shipping Address Details
    private Long addressId;
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


    @ManyToOne
    @JsonIgnore
    private PaymentOrders paymentOrders;


    @OneToOne(cascade = CascadeType.ALL )
    @JsonIgnore
    private OrderShippingAddress orderShippingAddresses;
}
