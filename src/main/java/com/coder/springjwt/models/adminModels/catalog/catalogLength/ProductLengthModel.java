package com.coder.springjwt.models.adminModels.catalog.catalogLength;

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
@Table(name = "ProductLength")
public class ProductLengthModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=1, max=10)
    @NotBlank(message = "Product Length must Not be Blank")
    @Column(unique = true)
    private String productLength;

    private String defaultName;

    private String description;

    private boolean isActive = Boolean.TRUE;
}
