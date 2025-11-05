package com.coder.springjwt.controllers.seller.InventoryController;

import com.coder.springjwt.constants.sellerConstants.sellerUrlMappings.SellerUrlMappings;
import com.coder.springjwt.dtos.sellerPayloads.sellerInventoryDtos.UpdateProductInventoryDto;
import com.coder.springjwt.services.sellerServices.inventoryService.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SellerUrlMappings.INVENTORY_CONTROLLER)
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping(SellerUrlMappings.GET_ALL_INVENTORY)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getAllInventory(@RequestParam Integer page ,
                                         @RequestParam  Integer size ,
                                         @RequestParam String username) {

        return this.inventoryService.getAllInventory(page , size ,username);
    }


    @PostMapping(SellerUrlMappings.UPDATE_PRODUCT_INVENTORY)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateProductInventory(@RequestBody UpdateProductInventoryDto updateProductInventoryDto) {
        return this.inventoryService.updateProductInventory(updateProductInventoryDto);
    }



}
