package com.coder.springjwt.services.adminServices.slidersService.homepageSliderService;

import com.coder.springjwt.dtos.adminDtos.slidersDtos.SliderDtos.SliderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface SliderService {

    ResponseEntity<?> createSlider(SliderDto sliderDto);

    ResponseEntity<?> getSlidersList();

    ResponseEntity<?> getSliderById(long sliderId);

    ResponseEntity<?> updateSlider(SliderDto sliderDto);

    ResponseEntity<?> deleteSliderById(long sliderId);

    ResponseEntity<?> updateSliderFile(MultipartFile file, Long sliderId);
}
