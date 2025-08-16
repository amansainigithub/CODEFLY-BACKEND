package com.coder.springjwt.models.sellerModels.SellerMobile;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "SellerMobile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellerMobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mobile;
    private Boolean isVerified = Boolean.FALSE;
    private String otp;
    private LocalDateTime expiresAt;


    // Getters and Setters

}
