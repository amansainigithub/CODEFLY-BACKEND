package com.coder.springjwt.models.props;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "api_props")
public class Api_Props extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @NotBlank
    private String provider;

    @NotBlank(message = "apikey must Not be Blank!!")
    @Column(length = 500 ,unique = true)
    private String apiKey;

    @NotBlank(message = "apiUrl must Not be Blank")
    @Column(unique = true)
    private String apiUrl;

    private String remarks;

    @Column(length = 20)
    private String status;

}
