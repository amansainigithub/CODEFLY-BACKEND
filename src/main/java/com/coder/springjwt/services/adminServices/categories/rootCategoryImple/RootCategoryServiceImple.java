package com.coder.springjwt.services.adminServices.categories.rootCategoryImple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.adminConstants.adminMessageConstants.AdminMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.RootCategoryDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.categories.RootCategoryModel;
import com.coder.springjwt.repository.adminRepository.categories.RootCategoryRepo;
import com.coder.springjwt.services.adminServices.categories.RootCategoryService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
public class RootCategoryServiceImple implements RootCategoryService {

    @Autowired
    RootCategoryRepo rootCategoryRepo;
    @Autowired
    BucketService bucketService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public ResponseEntity<?> saveRootCategory(RootCategoryDto rootCategoryDto) {
        MessageResponse response =new MessageResponse();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            rootCategoryDto.setUser(auth.getName());

            //Convert DTO TO Model Class...
            RootCategoryModel rootCategoryModel =  modelMapper.map(rootCategoryDto , RootCategoryModel.class);
            log.info("model mapper Conversion Success");

            //save Category
            this.rootCategoryRepo.save(rootCategoryModel);

            response.setMessage("Category Saved Success");
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response ,"Success");
        }
        catch (DataIntegrityViolationException ex) {
            // Handle exception here
            response.setMessage("Duplicate entry error: ");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response);
        }

    }

    @Override
    public ResponseEntity<?> getRootCategoryList() {

        try {
                log.info("First To Get From Database");
                // Fetch data from the database
                List<RootCategoryModel> rootList = this.rootCategoryRepo.findAll();
                return ResponseGenerator.generateSuccessResponse(rootList, AdminMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> deleteRootCategoryById(long categoryId) {
        try {
            RootCategoryModel data = this.rootCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new CategoryNotFoundException("Category id not Found"));

            this.rootCategoryRepo.deleteById(data.getId());
            log.info("Delete Success => Category id :: " + categoryId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , "Success");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Category Could Not deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("Category Could not deleted :: " + e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> getRootCategoryById(long categoryId) {
        try {
            RootCategoryModel data = this.rootCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            return ResponseGenerator.generateSuccessResponse(data , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> updateRootCategory(RootCategoryDto rootCategoryDto) {
        MessageResponse response = new MessageResponse();
        try {
            log.info(rootCategoryDto.toString());
            log.info("Update Process Starting....");
            RootCategoryModel rootData =  this.rootCategoryRepo.findById(rootCategoryDto.getId()).orElseThrow(()->new DataNotFoundException("Dara not Found"));

            //Delete Bucket -->AWS
            log.info("File Delete Success AWS");
            this.bucketService.deleteFile(rootData.getCategoryFile());

            RootCategoryModel rootCategoryModel =  modelMapper.map(rootCategoryDto , RootCategoryModel.class);
            this.rootCategoryRepo.save(rootCategoryModel);

            log.info("Data Update Success");
            return ResponseGenerator.generateSuccessResponse("Success" , "Data update Success");

        }
        catch (Exception e)
        {
            log.error("Data Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("failed" ," Data Update Failed");
        }
    }

    @Override
    public ResponseEntity<?> updateRootCategoryFile(MultipartFile file, Long rootCategoryId) {
        try {
            RootCategoryModel rootCategoryModel = this.rootCategoryRepo.findById(rootCategoryId).orElseThrow(()-> new DataNotFoundException("DATA_NOT_FOUND"));

            //Delete old File
            bucketService.deleteFile(rootCategoryModel.getCategoryFile());

            //upload New File
            BucketModel bucketModel = bucketService.uploadFile(file);
            if(bucketModel != null)
            {
                rootCategoryModel.setCategoryFile(bucketModel.getBucketUrl());
                this.rootCategoryRepo.save(rootCategoryModel);
                return ResponseGenerator.generateSuccessResponse("Success","File Update Success");
            }
            else {
                log.error("Bucket Model is null | please check AWS bucket configuration");
                throw new Exception("Bucket AWS is Empty");
            }
        }
        catch (Exception e)
        {
            log.error("Exception" , e.getMessage());
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Error" ,"File Not Update");
        }
    }
}
