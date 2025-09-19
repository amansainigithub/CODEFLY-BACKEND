package com.coder.springjwt.services.sellerServices.engineXService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface EngineXService {

    public ResponseEntity<?> getEngineXBuilder(long engineXId);
}
