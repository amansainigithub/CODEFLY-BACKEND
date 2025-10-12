package com.coder.springjwt.services.adminServices.categories.subCategoryImple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.SubCategoryDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.categories.RootCategoryModel;
import com.coder.springjwt.models.adminModels.categories.SubCategoryModel;
import com.coder.springjwt.repository.adminRepository.categories.RootCategoryRepo;
import com.coder.springjwt.repository.adminRepository.categories.SubCategoryRepo;
import com.coder.springjwt.services.adminServices.categories.SubCategoryService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SubCategoryServiceImple implements SubCategoryService {

    @Autowired
    SubCategoryRepo subCategoryRepo;

    @Autowired
    RootCategoryRepo rootCategoryRepo;

    @Autowired
    BucketService bucketService;


    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ResponseEntity<?> saveSubCategory(SubCategoryDto subCategoryDto) {
        MessageResponse response =new MessageResponse();
        try {
            SubCategoryModel subCategoryModel =  modelMapper.map(subCategoryDto , SubCategoryModel.class);
            log.info("Mapper Convert Success");

            Optional<RootCategoryModel> rootOptional=
                    this.rootCategoryRepo.findById(Long.parseLong(subCategoryDto.getRootCategoryId()));

            if(rootOptional.isPresent()) {
                log.info("Data Present Success");
                subCategoryModel.setRootCategory(rootOptional.get());

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                subCategoryModel.setUser(auth.getName());
                //save Category
                this.subCategoryRepo.save(subCategoryModel);

                log.info("SUB-Category Saved Success");
                response.setMessage("Child-Category Saved Success");
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(response, "Success");
            }
            else{
                log.error("Root Category Not Found Via Id : "+ subCategoryDto.getRootCategoryId());
                response.setMessage("Root Category Not Found Via Id : "+ subCategoryDto.getRootCategoryId());
                response.setStatus(HttpStatus.BAD_REQUEST);
                return ResponseGenerator.generateBadRequestResponse(response);
            }
        }
        catch (DataIntegrityViolationException ex) {
            // Handle exception here
            log.error("Duplicate entry error: ");
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
    public ResponseEntity<?> getSubCategoryList() {
        try {
            List<SubCategoryModel> subList =  this.subCategoryRepo.findAll();
            List<SubCategoryDto> subCategoryDtos =   subList
                    .stream()
                    .map(category -> modelMapper.map(category, SubCategoryDto.class))
                    .collect(Collectors.toList());
            return ResponseGenerator.generateSuccessResponse(subCategoryDtos,"Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Failed");
        }
    }

    @Override
    public ResponseEntity<?> deleteSubCategoryById(long categoryId) {
        try {
            SubCategoryModel data = this.subCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new CategoryNotFoundException("Category id not Found"));

            this.subCategoryRepo.deleteById(data.getId());
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
    public ResponseEntity<?> getSubCategoryById(long categoryId) {
        try {
            SubCategoryModel subCategoryModel = this.subCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            SubCategoryDto subCategoryDto = modelMapper.map(subCategoryModel, SubCategoryDto.class);
            log.info("Sub Category Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(subCategoryDto , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> updateSubCategory(SubCategoryDto subCategoryDto) {
        MessageResponse response = new MessageResponse();
        try {
            log.info(subCategoryDto.toString());
            log.info("Update Sub Process Starting....");
            this.subCategoryRepo.findById(subCategoryDto.getId()).orElseThrow(()->new DataNotFoundException("Data not Found"));

            SubCategoryModel subCategoryModel =  modelMapper.map(subCategoryDto , SubCategoryModel.class);
            this.subCategoryRepo.save(subCategoryModel);

            log.info("Data Update Success");
            return ResponseGenerator.generateSuccessResponse("Success" , "Data update Success");

        }
        catch (Exception e)
        {
            log.info("Data Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("failed" ," Data Update Failed");
        }
    }

    @Override
    public ResponseEntity<?> updateSubCategoryFile(MultipartFile file, Long subCategoryId) {
        try {
            SubCategoryModel subCategoryModel = this.subCategoryRepo.findById(subCategoryId).orElseThrow(()-> new DataNotFoundException("DATA_NOT_FOUND"));

            //Delete old File
            bucketService.deleteFile(subCategoryModel.getCategoryFile());

            //upload New File
            BucketModel bucketModel = bucketService.uploadFile(file);
            if(bucketModel != null)
            {
                subCategoryModel.setCategoryFile(bucketModel.getBucketUrl());
                this.subCategoryRepo.save(subCategoryModel);
                return ResponseGenerator.generateSuccessResponse("Success","File Update Success");
            }
            else {
                log.error("Bucket Model is null | please check AWS bucket configuration");
                throw new Exception("Bucket AWS is Empty");
            }
        }
        catch (Exception e)
        {
            log.info("Exception" , e.getMessage());
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Error" ,"File Not Update");
        }
    }
}
