package com.coder.springjwt.models.adminModels.catalog.catalogNetQuantity;

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
@Table(name = "ProductNetQuantity")
public class ProductNetQuantityModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=4, max=100)
    @NotBlank(message = "netQuantity must Not be Blank")
    private String netQuantity;

    private String defaultName;

    private String description;

    private boolean isActive = Boolean.FALSE;
}
