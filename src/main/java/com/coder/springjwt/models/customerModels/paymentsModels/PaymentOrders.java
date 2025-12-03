package com.coder.springjwt.models.customerModels.paymentsModels;

import com.coder.springjwt.models.customerModels.orders.OrderItems;
import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PaymentOrders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;
    private String paymentId;
    private String signature;
    private String amount;
    private String currency;
    private String status;
    private String created_at;;
    private String attempts;
    private String paymentMode;
    private String paymentCreatedJson;
    private String paymentCompleteJson;
    private String orderReferenceNo;
    private String userId;
    private String userName;

    private Long  addressId;

    private String paymentProvider;

    @OneToMany(cascade = CascadeType.ALL ,mappedBy ="paymentOrders" )
    @JsonIgnore
    private List<OrderItems> orderItems;
}
