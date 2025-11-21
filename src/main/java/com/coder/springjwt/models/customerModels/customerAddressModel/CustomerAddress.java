package com.coder.springjwt.models.customerModels.customerAddressModel;

import com.coder.springjwt.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CustomerAddress")
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String customerName;
    private String mobileNumber;
    private String area;
    private String postalCode;
    private String addressLine1;
    private String addressLine2;
    private boolean defaultAddress;

    private String username;
    private long userId;

    private String city;
    private String state;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customerId")
    private User user;

}
