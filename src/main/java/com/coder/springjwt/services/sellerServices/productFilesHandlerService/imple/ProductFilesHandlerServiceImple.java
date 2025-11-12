package com.coder.springjwt.services.sellerServices.productFilesHandlerService.imple;

import com.coder.springjwt.buckets.filesBucket.bucketService.BucketService;
import com.coder.springjwt.dtos.sellerPayloads.productFilesHandlerDtos.ProductFilesDto;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductFiles;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductFilesRepo;
import com.coder.springjwt.services.sellerServices.productFilesHandlerService.ProductFilesHandlerService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductFilesHandlerServiceImple implements ProductFilesHandlerService {

    @Autowired
    private ProductFilesRepo productFilesRepo;

    @Autowired
    private BucketService bucketService;

    @Autowired
    private ProductDetailsRepo productDetailsRepo;

    @Autowired
    private ProductFilesHandlerHelper productFilesHandlerHelper;

    @Autowired
    private UserHelper userHelper;


    @Override
    public ResponseEntity<?> getProductFilesByIdSeller(long productId, String username) {
        try {
            List<ProductFiles> productFilesData = this.productFilesRepo.findByProductDetailsId(productId);

            if(productFilesData.isEmpty())
            {
                throw new DataNotFoundException("Product Files is Empty");
            }

            List<ProductFilesDto> pfmList = new ArrayList<>();

            for(ProductFiles pf : productFilesData)
            {
                ProductFilesDto productFilesDto = new ProductFilesDto();
                productFilesDto.setId(pf.getId());
                productFilesDto.setProductId(pf.getProductDetailsId());
                productFilesDto.setFileName(pf.getFileName());
                productFilesDto.setFileUrl(pf.getFileUrl());
                productFilesDto.setFileType(pf.getFileType());
                pfmList.add(productFilesDto);
            }
            return ResponseGenerator.generateSuccessResponse(pfmList,"SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }

    @Override
    public ResponseEntity<?> modifiedProductFilesBySeller(MultipartFile files, 
                                                          String fileId, 
                                                          String productId, 
                                                          String username) {
        try {
            log.info("fileId: {}", fileId);
            log.info("productId: {}", productId);
            if (files == null || files.isEmpty()) {
                return ResponseGenerator.generateBadRequestResponse("Empty file provided.");
            }

            // VALIDATE CURRENT USER---
            Map<String, String> userData = userHelper.getCurrentUser();
            String sellerUsername = userData.get("username");
            if(!userData.get("username").trim().equals(username.trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }

            //Check Product with Username
            this.productDetailsRepo.findByIdAndUsername(Long.parseLong(productId) ,sellerUsername)
                    .orElseThrow(()-> new UsernameNotFoundException("Username Not Found Exception.."));


            if (!"null".equalsIgnoreCase(fileId)) {
                return this.productFilesHandlerHelper.updateExistingFile(files, fileId);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
        return ResponseGenerator.generateBadRequestResponse();
    }




    public ResponseEntity<?> modifiedProductVideoFilesBySeller(MultipartFile files,
                                                          String fileId,
                                                          String productId,
                                                          String username) {
        try {
            log.info("fileId: {}", fileId);
            log.info("productId: {}", productId);
            if (files == null || files.isEmpty()) {
                return ResponseGenerator.generateBadRequestResponse("Empty file provided.");
            }

            // VALIDATE CURRENT USER---
            Map<String, String> userData = userHelper.getCurrentUser();
            String sellerUsername = userData.get("username");
            if(!userData.get("username").trim().equals(username.trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }

            //Check Product with Username
            this.productDetailsRepo.findByIdAndUsername(Long.parseLong(productId) ,sellerUsername)
                    .orElseThrow(()-> new UsernameNotFoundException("Username Not Found Exception.."));


            if (!"null".equalsIgnoreCase(fileId)) {
                return this.productFilesHandlerHelper.updateExistingFile(files, fileId);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
        return ResponseGenerator.generateBadRequestResponse();
    }




    @Override
    public  ResponseEntity<?>  uploadNewFileBySeller(MultipartFile files, String productId, String username) {
        try {
            // VALIDATE CURRENT USER---
            Map<String, String> userData = userHelper.getCurrentUser();
            String sellerUsername = userData.get("username");
            if(!userData.get("username").trim().equals(username.trim()))
            {
                throw new UsernameNotFoundException("Username not found Exception...");
            }

            ProductDetailsModel productDetailsData = this.productDetailsRepo.findById(Long.parseLong(productId))
                    .orElseThrow(() -> new DataNotFoundException("Product Id Not Found for Product Upload New File | Seller"));

            String contentType = files.getContentType();
            
            if (contentType.startsWith("image/")){
                    return productFilesHandlerHelper.addNewImageFile(files, productDetailsData);

            } else if (contentType.startsWith("video/")) {
                    return productFilesHandlerHelper.addNewVideoFile(files, productDetailsData);
            }
            else{
                return ResponseGenerator.generateBadRequestResponse("Invalid File Format |Error");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Error | Something went wrong..!");
        }
    }

















}
