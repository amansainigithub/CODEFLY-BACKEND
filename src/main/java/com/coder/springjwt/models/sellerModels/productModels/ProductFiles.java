package com.coder.springjwt.models.sellerModels.productModels;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ProductFiles")
public class ProductFiles extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileName;

    private long fileSize;

    private String fileType;

    @Column(length = 1000)
    private String fileUrl;

    @ManyToOne
    @JoinColumn( referencedColumnName = "id" , name = "product_files_joiner")
    @JsonIgnore
    private ProductDetailsModel productDetailsModel;

}
