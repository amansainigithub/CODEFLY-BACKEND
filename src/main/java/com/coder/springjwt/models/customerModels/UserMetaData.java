package com.coder.springjwt.models.customerModels;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long userId;

    private String username;

    @Column(length = 255)
    private String browserDetails;

    @Column(length = 255)
    private String userAgent;

    @Column(length = 255)
    private String userAgentVersion;

    @Column(length = 255)
    private String operatingSystem;

    @Column(length = 255)
    private String browserName;


}
