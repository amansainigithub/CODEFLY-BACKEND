package com.coder.springjwt.models.props;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OtpRequestResponse")
@ToString
public class OtpRequestResponse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userName;

    private String areaMode;

    @Column(length = 1000)
    private String request;

    @Column(length = 1000)
    private String response;

    private String status;

    private String statusCode;

    private String userRole;

    private String mobileNumber;

    private String message;


}
