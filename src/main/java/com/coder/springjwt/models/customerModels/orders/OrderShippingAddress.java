package com.coder.springjwt.models.customerModels.orders;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OrderShippingAddress")
public class OrderShippingAddress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private String username;
    private long userId;

    private String orderNoPerItem;
    private String orderId;
    private String orderReferenceNo;
}
