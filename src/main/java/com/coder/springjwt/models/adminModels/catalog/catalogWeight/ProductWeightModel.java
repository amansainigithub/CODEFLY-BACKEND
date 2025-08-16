package com.coder.springjwt.models.adminModels.catalog.catalogWeight;

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
@Table(name = "ProductWeight")
public class ProductWeightModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=1, max=10)
    @NotBlank(message = "Product Weight must Not be Blank")
    @Column(unique = true)
    private String productWeight;

    private String defaultName;

    private String description;

    private boolean isActive = Boolean.TRUE;
}
