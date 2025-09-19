package com.coder.springjwt.models.sellerModels.engineXModels;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class EngineX extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long variantCategoryId;

    private String description;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String engineX;
}
