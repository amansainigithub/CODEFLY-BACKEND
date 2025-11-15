package com.coder.springjwt.models.adminModels.slidersModels;

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
@Table(name = "Slider")
public class SliderModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false )
    @Size(min=4, max=100)
    @NotBlank(message = "slider Category Name must Not be Blank")
    private String sliderCategory;

    @Column(length = 500)
    private String title;

    @Column(length = 500)
    private String subTitle;

    @Column(length = 1000)
    private String description;

    private String fileUrl;

    private String fileId;

    private String username;

    private String routingLink;

    private boolean isActive = Boolean.FALSE;
}
