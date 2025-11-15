package com.coder.springjwt.controllers.admin.slidersController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.slidersDtos.SliderDtos.SliderDto;
import com.coder.springjwt.services.adminServices.slidersService.homepageSliderService.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(AdminUrlMappings.SLIDER_CONTROLLER)
public class SliderController {

    @Autowired
    private SliderService sliderService;



    @PostMapping(AdminUrlMappings.CREATE_SLIDER)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createSlider(@Validated @RequestBody SliderDto sliderDto) {
        return this.sliderService.createSlider(sliderDto);
    }

    @GetMapping(AdminUrlMappings.GET_SLIDERS_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSlidersList() {
        return this.sliderService.getSlidersList();
    }


    @GetMapping(AdminUrlMappings.GET_SLIDER_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSliderById(@PathVariable long sliderId ) {
        return this.sliderService.getSliderById(sliderId);
    }


    @PostMapping(AdminUrlMappings.UPDATE_SLIDER)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSlider(@Validated @RequestBody SliderDto sliderDto ) {
        return this.sliderService.updateSlider(sliderDto);
    }


    @GetMapping(AdminUrlMappings.DELETE_SLIDER_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSliderById(@PathVariable long sliderId ) {
        return this.sliderService.deleteSliderById(sliderId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_SLIDER_FILE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSliderFile(@RequestParam(value = "file") MultipartFile file , @PathVariable Long sliderId)
    {
        return sliderService.updateSliderFile(file,sliderId);
    }
}
