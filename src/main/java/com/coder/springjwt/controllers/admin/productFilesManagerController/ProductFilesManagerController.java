package com.coder.springjwt.controllers.admin.productFilesManagerController;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.services.adminServices.productFilesManagerService.ProductFilesManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_FILES_MANAGER_CONTROLLER)
public class ProductFilesManagerController {

    @Autowired
    private ProductFilesManagerService productFilesManagerService;

    @GetMapping(AdminUrlMappings.GET_PRODUCT_FILES_BY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProductFilesById(@PathVariable long productId) {
        return this.productFilesManagerService.getProductFilesById(productId);
    }

    @PostMapping(AdminUrlMappings.UPLOAD_FILES)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadProductFiles(@PathVariable String productFileId,
                                                @PathVariable String productId,
                                                @RequestParam("files") MultipartFile files) {
        return this.productFilesManagerService.modifiedProductFiles(files, productFileId , productId);
    }

}
