package com.coder.springjwt.models.adminModels.catalog.catalogType;

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
@Table(name = "productType")
public class ProductTypeModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=4, max=100)
    @NotBlank(message = "type must Not be Blank" )
    @Column(unique = true)
    private String catalogType;

    private String defaultName;

    private String description;

    private boolean isActive = Boolean.TRUE;
}
