package com.coder.springjwt.controllers.admin.engineXBuilderAdminController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.services.adminServices.engineXBuilderAdminService.EngineXBuilderAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AdminUrlMappings.ENGINE_X_BUILDER_ADMIN_CONTROLLER)
public class engineXBuilderAdminController {

    @Autowired
    private EngineXBuilderAdminService engineXBuilderAdminService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(AdminUrlMappings.GET_ENGINE_X_BY_ADMIN)
    public ResponseEntity<?> getEngineXByAdmin(@PathVariable long engineXId) {
        return engineXBuilderAdminService.getEngineXByAdmin(engineXId);
    }
}
