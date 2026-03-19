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

    private String orderId;

    private String paymentState;

    private String userId;

    private String username;

    private String paymentMode;

    private String orderReferenceNo;

    private String orderNoPerItem;

    private String sellerId;

    private String sellerUsername;


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

    //ORDER STATUS [PENDING, DELIVERY ,HOLD ETC...]
    private String orderStatus;

    //Order Date
    private String orderDate;

    //Order Time
    private String orderTime;

    private Long variantId;

    @ManyToOne
    @JsonIgnore
    private PaymentOrders paymentOrders;



    //Product GST How Much Taken
    private String productSizeRowId;
    private String productGst;
    private String productTds;
    private String productTcs;
    private String productSkuCode;




    // SHIP-ROCKET UPDATED DATA IF ORDER IS CONFIRMED
    private String shipRocketOrderId;
    private String shipRocketChannelOrderId;
    private String shipRocketShipmentId;
    private String shipRocketStatus;
    private String shipRocketAwbCode;
    private String shipRocketCourierName;
    private String shipRocketCourierId;
    private Double shipRocketCourierPrice;
    private String shipRocketEtd;
    private String shipRocketDeliveryDays;
    private String shipRocketPickupStatus;
    private String shipRocketPickupDate;



    @OneToOne(cascade = CascadeType.ALL )
    @JsonIgnore
    private OrderShippingAddress orderShippingAddresses;
}
