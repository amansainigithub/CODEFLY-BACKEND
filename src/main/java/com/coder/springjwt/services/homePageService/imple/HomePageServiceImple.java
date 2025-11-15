package com.coder.springjwt.services.homePageService.imple;

import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.slidersModels.SliderModel;
import com.coder.springjwt.repository.sliderRepository.SliderRepository;
import com.coder.springjwt.services.homePageService.HomePageService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class HomePageServiceImple implements HomePageService {

    @Autowired
    private SliderRepository sliderRepository;


    @Override
    public ResponseEntity<?> fetchHomePage() {
        Map<Object,Object> mapNode = new HashMap<>();
        try {
            List<SliderModel> sliders = this.sliderRepository.findBySliderCategoryAndIsActive("HOMEPAGE_SLIDER" , Boolean.TRUE);
            if(sliders.isEmpty()) {
                throw new DataNotFoundException("No slider found | Please check slider Data");
            }
            mapNode.put("slider",sliders);

            return ResponseGenerator.generateSuccessResponse(mapNode,"SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }
}
