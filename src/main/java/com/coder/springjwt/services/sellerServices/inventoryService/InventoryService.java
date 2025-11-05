package com.coder.springjwt.services.sellerServices.inventoryService;

import com.coder.springjwt.dtos.sellerPayloads.sellerInventoryDtos.UpdateProductInventoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface InventoryService {
    ResponseEntity<?> getAllInventory(Integer page , Integer size, String username);

    ResponseEntity<?> getOutOfStockProduct(Integer page, Integer size, String username);
    ResponseEntity<?> getLowStockProduct(Integer page, Integer size, String username);

    ResponseEntity<?> updateProductInventory(UpdateProductInventoryDto updateProductInventoryDto);


}
