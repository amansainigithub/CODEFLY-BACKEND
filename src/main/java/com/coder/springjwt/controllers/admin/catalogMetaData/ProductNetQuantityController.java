package com.coder.springjwt.controllers.admin.catalogMetaData;

import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import com.coder.springjwt.dtos.adminDtos.catalogDtos.ProductNetQuantityDto;
import com.coder.springjwt.services.adminServices.catalogNetQuantityService.ProductNetQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminUrlMappings.PRODUCT_NET_QUANTITY_CONTROLLER)
public class ProductNetQuantityController {

    @Autowired
    private ProductNetQuantityService productNetQuantityService;

    @PostMapping(AdminUrlMappings.SAVE_NET_QUANTITY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveNetQuantity(@Validated @RequestBody ProductNetQuantityDto productNetQuantityDto) {
        return this.productNetQuantityService.saveNetQuantity(productNetQuantityDto);
    }

    @GetMapping(AdminUrlMappings.DELETE_NET_QUANTITY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteNetQuantity(@PathVariable long netQuantityId ) {
        return this.productNetQuantityService.deleteNetQuantity(netQuantityId);
    }


    @GetMapping(AdminUrlMappings.GET_NET_QUANTITY_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getNetQuantityById(@PathVariable long netQuantityId ) {
        return this.productNetQuantityService.getNetQuantityById(netQuantityId);
    }

    @PostMapping(AdminUrlMappings.UPDATE_NET_QUANTITY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateNetQuantity(@Validated @RequestBody ProductNetQuantityDto productNetQuantityDto) {
        return this.productNetQuantityService.updateNetQuantity(productNetQuantityDto);
    }

    @PostMapping(AdminUrlMappings.GET_NET_QUANTITY)
    public ResponseEntity<?> getNetQuantity(@RequestParam Integer page , @RequestParam  Integer size) {
        return this.productNetQuantityService.getNetQuantity(page,size);
    }

}
