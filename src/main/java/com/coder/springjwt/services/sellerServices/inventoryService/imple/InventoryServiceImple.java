package com.coder.springjwt.services.sellerServices.inventoryService.imple;

import com.coder.springjwt.dtos.sellerPayloads.sellerInventoryDtos.AllStockDto;
import com.coder.springjwt.dtos.sellerPayloads.sellerInventoryDtos.ProductInventoryDto;
import com.coder.springjwt.dtos.sellerPayloads.sellerInventoryDtos.UpdateProductInventoryDto;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductSizeRows;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductSizeRowsRepo;
import com.coder.springjwt.services.sellerServices.inventoryService.InventoryService;
import com.coder.springjwt.services.sellerServices.productOverviewService.imple.ProductOverviewServiceImple;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryServiceImple implements InventoryService {

    @Autowired
    private ProductDetailsRepo productDetailsRepo;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private ProductSizeRowsRepo productSizeRowsRepo;


    @Override
    public ResponseEntity<?> getAllInventory(Integer page, Integer size, String username) {
        try {
            log.info(ProductOverviewServiceImple.class.getName() + " working....");

            // VALIDATE CURRENT USER---
            Map<String, String> userData = userHelper.getCurrentUser();
            String currentUser= userData.get("username").trim();
            if(!userData.get("username").trim().equals(username.trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }

            Page<ProductDetailsModel> productDetails = this.productDetailsRepo
                                                        .findByUsername(currentUser, PageRequest.of(page, size,
                                                        Sort.by("productKey").descending()));

            List<AllStockDto> allStockDtoList = new ArrayList<>();
            for (ProductDetailsModel pdm : productDetails) {
                AllStockDto allStockDto = new AllStockDto();
                allStockDto.setId(pdm.getId());
                allStockDto.setProductName(pdm.getProductName());
                allStockDto.setProductStatus(pdm.getProductStatus());
                allStockDto.setProductKey(pdm.getProductKey());
                allStockDto.setProductDate(pdm.getProductDate());
                allStockDto.setProductTime(pdm.getProductTime());

                //Product Main File--
                try {
                    allStockDto.setProductMainFile(pdm.getProductFiles().get(0).getFileUrl());
                } catch (Exception e) {
                    allStockDto.setProductMainFile("BLANK");
                }

                List<ProductSizeRows> productSizeRows = pdm.getProductSizeRows();
                List<ProductInventoryDto> productInventoryCollector = productSizeRows.stream().map((e) -> {
                    ProductInventoryDto productInventoryDto = new ProductInventoryDto();
                    productInventoryDto.setId(e.getId());
                    productInventoryDto.setSize(e.get__msVal());
                    productInventoryDto.setInventory(e.getInventory());
                    return productInventoryDto;
                }).collect(Collectors.toList());

                /**---Set Product Size rows Or
                 Product Inventory to Product
                 Details for All Stock---**/
                allStockDto.setProductInventories(productInventoryCollector);

                allStockDtoList.add(allStockDto);
            }

            // DTO list convert to Page Wrap
            Page<AllStockDto> allStockData =
                    new PageImpl<>(allStockDtoList, productDetails.getPageable(), productDetails.getTotalElements());
            return ResponseGenerator.generateSuccessResponse(allStockData, "SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("BAD REQUEST");
        }
    }

    @Override
    public ResponseEntity<?> getOutOfStockProduct(Integer page, Integer size, String username) {
        try {
            log.info(ProductOverviewServiceImple.class.getName() + " working....");

            // VALIDATE CURRENT USER---
            Map<String, String> userData = userHelper.getCurrentUser();
            String currentUser= userData.get("username").trim();
            if(!userData.get("username").trim().equals(username.trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }

            Page<ProductDetailsModel> productDetails = this.productDetailsRepo.
                                                        getProductsByInventoryZeroAndUserName(username ,
                                                        PageRequest.of(page, size,
                                                        Sort.by("productKey").descending()));

            List<AllStockDto> allStockDtoList = new ArrayList<>();
            for (ProductDetailsModel pdm : productDetails) {
                AllStockDto allStockDto = new AllStockDto();
                allStockDto.setId(pdm.getId());
                allStockDto.setProductName(pdm.getProductName());
                allStockDto.setProductStatus(pdm.getProductStatus());
                allStockDto.setProductKey(pdm.getProductKey());
                allStockDto.setProductDate(pdm.getProductDate());
                allStockDto.setProductTime(pdm.getProductTime());

                //Product Main File--
                try {
                    allStockDto.setProductMainFile(pdm.getProductFiles().get(0).getFileUrl());
                } catch (Exception e) {
                    allStockDto.setProductMainFile("BLANK");
                }

                List<ProductSizeRows> productSizeRows = pdm.getProductSizeRows();
                List<ProductInventoryDto> productInventoryCollector = new ArrayList<>();
                for (ProductSizeRows psr : productSizeRows) {
                        ProductInventoryDto productInventoryDto = new ProductInventoryDto();
                        productInventoryDto.setId(psr.getId());
                        productInventoryDto.setSize(psr.get__msVal());
                        productInventoryDto.setInventory(psr.getInventory());
                        productInventoryCollector.add(productInventoryDto);

                }
                    /**---Set Product Size rows Or
                     Product Inventory to Product
                     Details for Out Of Stock---**/
                    allStockDto.setProductInventories(productInventoryCollector);
                    allStockDtoList.add(allStockDto);
            }

            // DTO list convert to Page Wrap
            Page<AllStockDto> stockData =
                    new PageImpl<>(allStockDtoList, productDetails.getPageable(), productDetails.getTotalElements());
            return ResponseGenerator.generateSuccessResponse(stockData, "SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("BAD REQUEST");
        }
    }

    @Override
    public ResponseEntity<?> getLowStockProduct(Integer page, Integer size, String username) {
        try {
            log.info(ProductOverviewServiceImple.class.getName() + " working....");

            // VALIDATE CURRENT USER---
            Map<String, String> userData = userHelper.getCurrentUser();
            String currentUser= userData.get("username").trim();
            if(!userData.get("username").trim().equals(username.trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }

            Page<ProductDetailsModel> productDetails = this.productDetailsRepo.
                    findLowInventoryProductsByUsername(username ,
                            PageRequest.of(page, size,
                                    Sort.by("productKey").descending()));

            List<AllStockDto> allStockDtoList = new ArrayList<>();
            for (ProductDetailsModel pdm : productDetails) {
                AllStockDto allStockDto = new AllStockDto();
                allStockDto.setId(pdm.getId());
                allStockDto.setProductName(pdm.getProductName());
                allStockDto.setProductStatus(pdm.getProductStatus());
                allStockDto.setProductKey(pdm.getProductKey());
                allStockDto.setProductDate(pdm.getProductDate());
                allStockDto.setProductTime(pdm.getProductTime());

                //Product Main File--
                try {
                    allStockDto.setProductMainFile(pdm.getProductFiles().get(0).getFileUrl());
                } catch (Exception e) {
                    allStockDto.setProductMainFile("BLANK");
                }

                List<ProductSizeRows> productSizeRows = pdm.getProductSizeRows();
                List<ProductInventoryDto> productInventoryCollector = new ArrayList<>();
                for (ProductSizeRows psr : productSizeRows) {
                    ProductInventoryDto productInventoryDto = new ProductInventoryDto();
                    productInventoryDto.setId(psr.getId());
                    productInventoryDto.setSize(psr.get__msVal());
                    productInventoryDto.setInventory(psr.getInventory());
                    productInventoryCollector.add(productInventoryDto);

                }
                /**---Set Product Size rows Or
                 Product Inventory to Product
                 Details for Low Stocks---**/
                allStockDto.setProductInventories(productInventoryCollector);
                allStockDtoList.add(allStockDto);
            }

            // DTO list convert to Page Wrap
            Page<AllStockDto> stockData =
                    new PageImpl<>(allStockDtoList, productDetails.getPageable(), productDetails.getTotalElements());
            return ResponseGenerator.generateSuccessResponse(stockData, "SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("BAD REQUEST");
        }
    }

    @Override
    public ResponseEntity<?> updateProductInventory(UpdateProductInventoryDto updateProductInventoryDto) {
        try {
            // VALIDATE CURRENT USER---
            Map<String, String> userData = userHelper.getCurrentUser();
            if(!userData.get("username").trim().equals(updateProductInventoryDto.getUsername().trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }
            // VALIDATE CURRENT USER ENDING---
            ProductSizeRows productSizeRows = this.productSizeRowsRepo.findById(updateProductInventoryDto.getId())
                    .orElseThrow(() -> new DataNotFoundException("Product Size Rows | Data Not Found Exception "));

            productSizeRows.setInventory(updateProductInventoryDto.getInventory());

            this.productSizeRowsRepo.save(productSizeRows);
            log.info("Update Product Size Rows Success...");
            return ResponseGenerator.generateSuccessResponse("Update Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }
}
