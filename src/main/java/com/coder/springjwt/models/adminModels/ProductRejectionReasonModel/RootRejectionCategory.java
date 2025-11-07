package com.coder.springjwt.models.adminModels.ProductRejectionReasonModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RootRejectionCategory")
@Entity
public class RootRejectionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Root Rejection Category" )
    @Column(unique = true)
    private String rootRejectionCategory;

    @Column(length = 500)
    private String description;

    private boolean isActive = Boolean.FALSE;

    @OneToMany(mappedBy = "rootRejectionCategories",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductRejectionReason> productRejectionReasons;

}
