package com.coder.springjwt.controllers.admin.chargeConfigController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.chargeConfigDtos.ChargeConfigDto;
import com.coder.springjwt.services.adminServices.chargeConfigService.ChargeConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.CHARGE_CONFIG_CONTROLLER)
public class ChargeConfigController {


    @Autowired
    private ChargeConfigService chargeConfigService;

    @PostMapping(AdminUrlMappings.CREATE_CHARGE_CONFIG)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createChargeConfig(@Validated @RequestBody ChargeConfigDto chargeConfigDto) {
        return this.chargeConfigService.saveChargeConfig(chargeConfigDto);
    }

    @GetMapping(AdminUrlMappings.GET_CHARGE_CONFIG_LIST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getChargeConfigList() {
        return this.chargeConfigService.getChargeConfigList();
    }


    @GetMapping(AdminUrlMappings.DELETE_CHARGE_CONFIG)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteChargeConfig(@PathVariable long chargeId ) {
        return this.chargeConfigService.deleteChargeConfig(chargeId);
    }

    @GetMapping(AdminUrlMappings.GET_CHARGE_CONFIG_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getChargeConfigById(@PathVariable long chargeId ) {
        return this.chargeConfigService.getChargeConfigById(chargeId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_CHARGE_CONFIG)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateChargeConfig(@Validated  @RequestBody ChargeConfigDto chargeConfigDto ) {
        return this.chargeConfigService.updateChargeConfig(chargeConfigDto);
    }


}
