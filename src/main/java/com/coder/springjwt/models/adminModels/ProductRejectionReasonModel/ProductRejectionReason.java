package com.coder.springjwt.models.adminModels.ProductRejectionReasonModel;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ProductRejectionReason")
@Entity
public class ProductRejectionReason extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @Size(min=4, max=100)
    @NotBlank(message = "code must Not be Blank" )
    @Column(unique = true)
    private String code;

    private String reason;

    private String description;

    private boolean isActive = Boolean.FALSE;
}
