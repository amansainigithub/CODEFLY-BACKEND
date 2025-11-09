package com.coder.springjwt.services.adminServices.productFilesManagerService.imple;

import com.coder.springjwt.buckets.filesBucket.bucketService.BucketService;
import com.coder.springjwt.dtos.adminDtos.productFilesManagerDto.ProductFilesManagerDto;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.productModels.ProductFiles;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductFilesRepo;
import com.coder.springjwt.services.adminServices.productFilesManagerService.ProductFilesManagerService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductFilesManagerServiceImple implements ProductFilesManagerService {

    @Autowired
    private ProductFilesRepo productFilesRepo;

    @Autowired
    private BucketService bucketService;

    @Autowired
    private ProductDetailsRepo productDetailsRepo;

    @Autowired
    private ProductFilesManagerHelper productFilesManagerHelper;

    @Override
    public ResponseEntity<?> getProductFilesById(long productId) {
        try {
            List<ProductFiles> productFilesData = this.productFilesRepo.findByProductDetailsId(productId);

            if(productFilesData.isEmpty())
            {
                throw new DataNotFoundException("Product Files is Empty");
            }

            List<ProductFilesManagerDto> pfmList = new ArrayList<>();

            for(ProductFiles pf : productFilesData)
            {
                ProductFilesManagerDto productFilesManagerDto = new ProductFilesManagerDto();
                productFilesManagerDto.setId(pf.getId());
                productFilesManagerDto.setProductId(pf.getProductDetailsId());
                productFilesManagerDto.setFileName(pf.getFileName());
                productFilesManagerDto.setFileUrl(pf.getFileUrl());
                productFilesManagerDto.setFileType(pf.getFileType());
                pfmList.add(productFilesManagerDto);
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
    public ResponseEntity<?> modifiedProductFiles(MultipartFile file, String productFileId, String productId) {
        try {
            log.info("productFileId: {}", productFileId);
            log.info("productId: {}", productId);

            if (file == null || file.isEmpty()) {
                return ResponseGenerator.generateBadRequestResponse("Empty file provided.");
            }

            if (!"null".equalsIgnoreCase(productFileId)) {
                return productFilesManagerHelper.updateExistingFile(file, productFileId);
            } else {
                return productFilesManagerHelper.addNewFile(file, productId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Product file update failed.");
        }
    }




}
