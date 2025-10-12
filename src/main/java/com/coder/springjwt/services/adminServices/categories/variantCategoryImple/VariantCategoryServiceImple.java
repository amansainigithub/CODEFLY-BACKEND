package com.coder.springjwt.services.adminServices.categories.variantCategoryImple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.VariantCategoryDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.categories.TypeCategoryModel;
import com.coder.springjwt.models.adminModels.categories.VariantCategoryModel;
import com.coder.springjwt.repository.adminRepository.categories.TypeCategoryRepo;
import com.coder.springjwt.repository.adminRepository.categories.VariantCategoryRepo;
import com.coder.springjwt.services.adminServices.categories.VariantCategoryService;
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
public class VariantCategoryServiceImple implements VariantCategoryService {

    @Autowired
    TypeCategoryRepo typeCategoryRepo;

    @Autowired
    VariantCategoryRepo variantCategoryRepo;

    @Autowired
    BucketService bucketService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> saveVariantCategory(VariantCategoryDto variantCategoryDto) {
        MessageResponse response =new MessageResponse();
        try {
            VariantCategoryModel variantCategoryModel=  modelMapper.map(variantCategoryDto , VariantCategoryModel.class);

            Optional<TypeCategoryModel> typeOptional =
                    this.typeCategoryRepo.findById(Long.parseLong(variantCategoryDto.getTypeCategoryId()));

            if(typeOptional.isPresent()) {
                log.info("Data Present Success");
                variantCategoryModel.setTypeCategoryModel(typeOptional.get());

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                variantCategoryModel.setUser(auth.getName());
                //save Category
                this.variantCategoryRepo.save(variantCategoryModel);

                log.info("Born-Category Saved Success");
                response.setMessage("Born-Category Saved Success");
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(response, "Success");
            }
            else{
                log.error("Type Category Not Found Via Id : "+ variantCategoryDto.getTypeCategoryId());
                response.setMessage("Type Category Not Found Via Id : "+ variantCategoryDto.getTypeCategoryId());
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
    public ResponseEntity<?> getVariantCategoryList() {
        try {
            List<VariantCategoryModel> variantList =  this.variantCategoryRepo.findAll();
            List<VariantCategoryDto> variantCategoryDtos =   variantList
                    .stream()
                    .map(category -> modelMapper.map(category, VariantCategoryDto.class))
                    .collect(Collectors.toList());
            return ResponseGenerator.generateSuccessResponse(variantCategoryDtos,"Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Failed");
        }
    }

    @Override
    public ResponseEntity<?> deleteVariantCategoryById(long categoryId) {
        try {
            VariantCategoryModel data = this.variantCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new CategoryNotFoundException("Category id not Found"));

            this.variantCategoryRepo.deleteById(data.getId());
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
    public ResponseEntity<?> updateVariantCategory(VariantCategoryDto variantCategoryDto) {
        MessageResponse response = new MessageResponse();
        try {

            log.info(variantCategoryDto.toString());

            //get Born Data
            VariantCategoryModel variantData =   this.variantCategoryRepo.findById(variantCategoryDto.getId()).
                    orElseThrow(()->new DataNotFoundException("Data not Found"));

            //getBaby Category By ID
            TypeCategoryModel typeData =   this.typeCategoryRepo.findById(Long.valueOf(variantCategoryDto.getTypeCategoryId())).
                    orElseThrow(()->new DataNotFoundException("Data not Found"));


//            Convert DTO TO Model Class
            VariantCategoryModel variantCategoryModel =  modelMapper.map(variantCategoryDto , VariantCategoryModel.class);

            //Set baby Data in Modal
            variantCategoryModel.setTypeCategoryModel(typeData);

            this.variantCategoryRepo.save(variantCategoryModel);

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
    public ResponseEntity<?> getVariantCategoryById(long categoryId) {
        try {
            VariantCategoryModel variantCategoryModel = this.variantCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            VariantCategoryDto variantCategoryDto = modelMapper.map(variantCategoryModel, VariantCategoryDto.class);
            log.info("Born Category Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(variantCategoryDto , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> updateVariantCategoryFile(MultipartFile file, Long variantCategoryId) {
        try {
            VariantCategoryModel variantCategoryModel = this.variantCategoryRepo.findById(variantCategoryId).orElseThrow(()-> new DataNotFoundException("DATA_NOT_FOUND"));

            //Delete old File
            try {
                bucketService.deleteFile(variantCategoryModel.getCategoryFile());
            }catch (Exception e)
            {
                log.error("File Not deleted Id:: " + variantCategoryId);
            }
            //upload New File
            BucketModel bucketModel = bucketService.uploadFile(file);
            if(bucketModel != null)
            {
                variantCategoryModel.setCategoryFile(bucketModel.getBucketUrl());
                this.variantCategoryRepo.save(variantCategoryModel);
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
