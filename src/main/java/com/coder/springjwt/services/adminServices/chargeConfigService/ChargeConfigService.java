package com.coder.springjwt.services.adminServices.chargeConfigService;

import com.coder.springjwt.dtos.adminDtos.chargeConfigDtos.ChargeConfigDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ChargeConfigService {
    ResponseEntity<?> saveChargeConfig(ChargeConfigDto chargeConfigDto);

    ResponseEntity<?> getChargeConfigList();

    ResponseEntity<?> deleteChargeConfig(long chargeId);

    ResponseEntity<?> getChargeConfigById(long chargeId);

    ResponseEntity<?> updateChargeConfig(ChargeConfigDto chargeConfigDto);
}
