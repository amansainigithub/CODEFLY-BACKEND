package com.coder.springjwt.models.adminModels.catalog.catalogSize;

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
@Table(name = "productSizeVariant")
public class ProductSizeVariantModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=4, max=100)
    @NotBlank(message = "Size must Not be Blank" )
    @Column(unique = true)
    private String size;

    private String defaultName;

    private String description;

    private boolean isActive = Boolean.TRUE;
}
