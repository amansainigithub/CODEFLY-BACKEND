package com.coder.springjwt.services.adminServices.gstPercentageService;

import com.coder.springjwt.dtos.adminDtos.catalogDtos.GstPercentageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface GstPercentageService {

    ResponseEntity<?> saveGstPercentage(GstPercentageDto gstPercentageDto);

    ResponseEntity<?> getGstPercentage(Integer page, Integer size);
}
