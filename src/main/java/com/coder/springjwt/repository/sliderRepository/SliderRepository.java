package com.coder.springjwt.repository.sliderRepository;

import com.coder.springjwt.models.adminModels.slidersModels.SliderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SliderRepository  extends JpaRepository<SliderModel,Long> {
}
