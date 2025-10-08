package com.coder.springjwt.models.adminModels.productStatusTracker;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ProductStatusTracker")
public class ProductStatusTracker extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long productId;

    private String statusDate;

    private String statusTime;

    private String statusDateTime;

    private long reasonId;

    private String Reason;

    private String productDescription;


}
