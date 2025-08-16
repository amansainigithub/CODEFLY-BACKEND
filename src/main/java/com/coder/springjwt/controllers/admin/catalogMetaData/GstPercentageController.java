package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.GstPercentageDto;
import com.coder.springjwt.services.adminServices.gstPercentageService.GstPercentageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.GST_PERCENTAGE_CONTROLLER)
public class GstPercentageController {

    @Autowired
    private GstPercentageService gstPercentageService ;

    @PostMapping(AdminUrlMappings.SAVE_GST_PERCENTAGE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveGstPercentage(@Validated @RequestBody GstPercentageDto gstPercentageDto) {
        return this.gstPercentageService.saveGstPercentage(gstPercentageDto);
    }

    @PostMapping(AdminUrlMappings.GET_GST_PERCENTAGE)
    @PreAuthorize("hasRole('ADMIN') ")
    public ResponseEntity<?> getGstPercentage(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.gstPercentageService.getGstPercentage(page,size);
    }

}
