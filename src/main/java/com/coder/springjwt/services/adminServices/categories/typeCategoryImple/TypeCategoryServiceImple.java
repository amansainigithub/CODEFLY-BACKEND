package com.coder.springjwt.services.adminServices.categories.typeCategoryImple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.TypeCategoryDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.categories.SubCategoryModel;
import com.coder.springjwt.models.adminModels.categories.TypeCategoryModel;
import com.coder.springjwt.repository.adminRepository.categories.SubCategoryRepo;
import com.coder.springjwt.repository.adminRepository.categories.TypeCategoryRepo;
import com.coder.springjwt.services.adminServices.categories.TypeCategoryService;
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
public class TypeCategoryServiceImple implements TypeCategoryService {

    @Autowired
    TypeCategoryRepo typeCategoryRepo;

    @Autowired
    SubCategoryRepo subCategoryRepo;

    @Autowired
    BucketService bucketService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> saveTypeCategory(TypeCategoryDto typeCategoryDto) {
        MessageResponse response =new MessageResponse();
        try {
            TypeCategoryModel typeCategoryModel =  modelMapper.map(typeCategoryDto , TypeCategoryModel.class);
            log.info("Mapper Convert Success");

            Optional<SubCategoryModel> subOptional =
                    this.subCategoryRepo.findById(Long.parseLong(typeCategoryDto.getSubCategoryId()));

            if(subOptional.isPresent()) {
                log.info("Data Present Success");
                typeCategoryModel.setSubCategoryModel(subOptional.get());

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                typeCategoryModel.setUser(auth.getName());
                //save Category
                this.typeCategoryRepo.save(typeCategoryModel);

                log.info("Type-Category Saved Success");
                response.setMessage("Type-Category Saved Success");
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(response, "Success");
            }
            else{
                log.error("Sub Category Not Found Via Id : "+ typeCategoryDto.getSubCategoryId());
                response.setMessage("Sub Category Not Found Via Id : "+ typeCategoryDto.getSubCategoryId());
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
    public ResponseEntity<?> getTypeCategoryList() {
        try {
            List<TypeCategoryModel> typeList =  this.typeCategoryRepo.findAll();
            List<TypeCategoryDto> typeCategoryDtos =   typeList
                    .stream()
                    .map(category -> modelMapper.map(category, TypeCategoryDto.class))
                    .collect(Collectors.toList());
            return ResponseGenerator.generateSuccessResponse(typeCategoryDtos,"Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Failed");
        }
    }

    @Override
    public ResponseEntity<?> deleteTypeCategoryById(long categoryId) {
        try {
            TypeCategoryModel data = this.typeCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new CategoryNotFoundException("Category id not Found"));

            this.typeCategoryRepo.deleteById(data.getId());
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
    public ResponseEntity<?> updateTypeCategory(TypeCategoryDto typeCategoryDto) {
        MessageResponse response = new MessageResponse();
        try {

            log.info(typeCategoryDto.toString());

            this.typeCategoryRepo.findById(typeCategoryDto.getId()).orElseThrow(()->new DataNotFoundException("Data not Found"));
            TypeCategoryModel typeCategoryModel =  modelMapper.map(typeCategoryDto , TypeCategoryModel.class);

            //Get Child Data
            SubCategoryModel subCategoryModel =
                    this.subCategoryRepo.findById(Long.valueOf(typeCategoryDto.getSubCategoryId())).get();
            typeCategoryModel.setSubCategoryModel(subCategoryModel);

            this.typeCategoryRepo.save(typeCategoryModel);

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
    public ResponseEntity<?> getTypeCategoryById(long categoryId) {
        try {
            TypeCategoryModel typeCategoryModel = this.typeCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            TypeCategoryDto typeCategoryDto = modelMapper.map(typeCategoryModel, TypeCategoryDto.class);
            log.info("Child Category Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(typeCategoryDto , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> updateTypeCategoryFile(MultipartFile file, Long typeCategoryId) {
        try {
            TypeCategoryModel typeCategoryModel = this.typeCategoryRepo.findById(typeCategoryId).orElseThrow(()-> new DataNotFoundException("DATA_NOT_FOUND"));

            //Delete old File
            try {
                bucketService.deleteFile(typeCategoryModel.getCategoryFile());
            }catch (Exception e)
            {
                log.error("File Not deleted Id:: " + typeCategoryId);
            }
            //upload New File
            BucketModel bucketModel = bucketService.uploadFile(file);
            if(bucketModel != null)
            {
                typeCategoryModel.setCategoryFile(bucketModel.getBucketUrl());
                this.typeCategoryRepo.save(typeCategoryModel);
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
