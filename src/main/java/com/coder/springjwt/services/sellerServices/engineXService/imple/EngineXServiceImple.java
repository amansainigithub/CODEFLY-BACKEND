package com.coder.springjwt.services.sellerServices.engineXService.imple;

import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.chargeConfigModels.ChargeConfig;
import com.coder.springjwt.models.sellerModels.engineXModels.EngineX;
import com.coder.springjwt.repository.adminRepository.chargeConfigRepo.ChargeConfigRepo;
import com.coder.springjwt.repository.sellerRepository.engineXRepository.EngineXRepository;
import com.coder.springjwt.services.sellerServices.engineXService.EngineXService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class EngineXServiceImple implements EngineXService {

    @Autowired
    private EngineXRepository engineXRepository;

    @Autowired
    private ChargeConfigRepo chargeConfigRepo;

    @Override
    public ResponseEntity<?> getEngineXBuilder(long engineXId) {
        log.info("Engine-X-Builder Working...");
        try {
            Optional<EngineX> engineXData = this.engineXRepository.findByVariantCategoryId(engineXId);
            if(!engineXData.isEmpty())
            {
                EngineX engineX = engineXData.get();
                return ResponseEntity.ok(engineX.getEngineX());
            }else {
                log.info("Dummy Data Engine-X");
                String engineXDummyData = EngineXDummyData.getDummyData();
                return ResponseEntity.ok(engineXDummyData);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<?> getChargesBySeller(String id) {
        log.info("Engine-X-Builder Working...");
        try {
            ChargeConfig chargeConfig = this.chargeConfigRepo.findByVariantId(id)
                    .orElseThrow(() -> new DataNotFoundException("Variant Id Not Found ID :: " + id));
                return ResponseGenerator.generateSuccessResponse(chargeConfig , "SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }


}
