package com.coder.springjwt.controllers.seller.engineXController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.services.sellerServices.engineXService.EngineXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SellerUrlMappings.ENGINE_X_BUILDER_CONTROLLER)
public class EngineXController {

    @Autowired
    private EngineXService engineXService;

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping(SellerUrlMappings.GET_ENGINE_X)
    public ResponseEntity<?> getEngineX(@PathVariable long engineXId) {
        return engineXService.getEngineXBuilder(engineXId);
    }
}
