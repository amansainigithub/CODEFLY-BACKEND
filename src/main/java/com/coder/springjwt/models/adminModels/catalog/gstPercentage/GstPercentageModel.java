package com.coder.springjwt.models.adminModels.catalog.gstPercentage;

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
@Table(name = "GstPercentage")
public class GstPercentageModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=1, max=10)
    @NotBlank(message = "Gst Percentage must Not be Blank")
    @Column(unique = true)
    private String gstPercentage;

    private String defaultName;

    private String description;

    private boolean isActive = Boolean.TRUE;
}
