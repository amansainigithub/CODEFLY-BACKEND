package com.coder.springjwt.models.adminModels.chargeConfigModels;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ChargeConfig")
public class ChargeConfig extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String variantId;

    @Size(min=4, max=100)
    @NotBlank(message = "VariantName must Not be Blank" )
    @Column(unique = true)
    private String variantName;

    private String tcsCharge;

    private String tdsCharge;

    private String shippingCharge;

    private String shippingChargeFee;

    private String description;

    private boolean isActive = Boolean.FALSE;
}
