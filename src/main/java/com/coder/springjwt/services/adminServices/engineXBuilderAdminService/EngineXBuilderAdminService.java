package com.coder.springjwt.services.adminServices.engineXBuilderAdminService;

import org.springframework.http.ResponseEntity;

public interface EngineXBuilderAdminService {
    public ResponseEntity<?> getEngineXByAdmin(long engineXId);
}
