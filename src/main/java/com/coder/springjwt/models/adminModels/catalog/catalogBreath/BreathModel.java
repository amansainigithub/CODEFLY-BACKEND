package com.coder.springjwt.models.adminModels.catalog.catalogBreath;

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
@Table(name = "ProductBreath")
public class BreathModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=4, max=100)
    @NotBlank(message = "product Breath  must Not be Blank" )
    @Column(unique = true)
    private String productBreath;

    private String defaultName;

    private String description;

    private boolean isActive = Boolean.TRUE;
}
