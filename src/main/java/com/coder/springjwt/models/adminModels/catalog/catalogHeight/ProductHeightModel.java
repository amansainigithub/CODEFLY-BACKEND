package com.coder.springjwt.models.adminModels.catalog.catalogHeight;

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
@Table(name = "ProductHeight")
public class ProductHeightModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=4, max=100)
    @NotBlank(message = "product Height must Not be Blank")
    @Column(unique = true)
    private String productHeight;

    private String defaultName;

    private String description;

    private boolean isActive = Boolean.FALSE;
}
