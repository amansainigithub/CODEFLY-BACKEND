package com.coder.springjwt.services.sellerServices.engineXService.imple;

import com.coder.springjwt.models.sellerModels.engineXModels.EngineX;
import com.coder.springjwt.repository.sellerRepository.engineXRepository.EngineXRepository;
import com.coder.springjwt.services.sellerServices.engineXService.EngineXService;
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
}
