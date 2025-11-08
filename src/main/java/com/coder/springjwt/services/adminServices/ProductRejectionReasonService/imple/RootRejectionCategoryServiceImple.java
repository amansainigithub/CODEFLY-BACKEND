package com.coder.springjwt.services.adminServices.ProductRejectionReasonService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.productRejectionReasonDto.RootRejectionCategoryDto;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.ProductRejectionReasonModel.RootRejectionCategory;
import com.coder.springjwt.repository.productRejectionReasonRepo.RootRejectionCategoryRepo;
import com.coder.springjwt.services.adminServices.ProductRejectionReasonService.RootRejectionCategoryService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RootRejectionCategoryServiceImple implements RootRejectionCategoryService {

    @Autowired
    private RootRejectionCategoryRepo rootRejectionCategoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> createRootRejectionCategory(RootRejectionCategoryDto rootRejectionCategoryDto) {
        MessageResponse response =new MessageResponse();
        try {
            RootRejectionCategory rootRejectionCategory =  modelMapper.map(rootRejectionCategoryDto, RootRejectionCategory.class);
            log.info("Object Mapper Convert Success");

            // Root Rejection Category
            this.rootRejectionCategoryRepo.save(rootRejectionCategory);

            response.setMessage("Root Rejection Reason Saved Success");
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response, SellerMessageResponse.SUCCESS);

        }
        catch (DataIntegrityViolationException ex) {
            response.setMessage("Duplicate Entry Error: ");
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
    public ResponseEntity<?> getRootRejectionCategory() {
        try {
            List<RootRejectionCategory> rootRejectionCategories =  this.rootRejectionCategoryRepo.findAll();

            List<RootRejectionCategoryDto> rootRejectionData = rootRejectionCategories.stream()
                    .map(data -> modelMapper.map(data, RootRejectionCategoryDto.class))
                    .collect(Collectors.toList());
            return ResponseGenerator.generateSuccessResponse(rootRejectionData,"SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Failed");
        }
    }

    @Override
    public ResponseEntity<?> deleteRootRejectionCategory(long rejectionId) {
        try {
            RootRejectionCategory rootRejectionCategory = this.rootRejectionCategoryRepo.findById(rejectionId).orElseThrow(
                    () -> new DataNotFoundException("Root Rejection Category id not Found"));
            this.rootRejectionCategoryRepo.delete(rootRejectionCategory);
            log.info("Delete Root Rejection Category Success => Rejection ID :: " + rejectionId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Root Rejection Category Could Not deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("Root Rejection Category Could not deleted :: " + e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> getRootRejectionCategoryById(long rejectionId) {
        try {
            RootRejectionCategory rootRejectionCategory = this.rootRejectionCategoryRepo.findById(rejectionId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            RootRejectionCategoryDto rootRejectionCategoryDto = modelMapper.map(rootRejectionCategory, RootRejectionCategoryDto.class);
            log.info("Root Rejection Category Fetch Success");
            return ResponseGenerator.generateSuccessResponse(rootRejectionCategoryDto , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Could Not fetch Root Rejection Category");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> updateRootRejectionCategory(RootRejectionCategoryDto rootRejectionCategoryDto) {
        try {
            log.info(rootRejectionCategoryDto.toString());

            //get rejection Reason Data
            this.rootRejectionCategoryRepo.findById(rootRejectionCategoryDto.getId())
                    .orElseThrow(() -> new DataNotFoundException("Root Rejection Category Not Found !"));

//            Convert DTO TO Model Class
            RootRejectionCategory rootRejectionCategory=  modelMapper.map(rootRejectionCategoryDto , RootRejectionCategory.class);
            this.rootRejectionCategoryRepo.save(rootRejectionCategory);
            log.info("Root Rejection Category Update Success");
            return ResponseGenerator.generateSuccessResponse("Success" , "Data update Success");

        }
        catch (Exception e)
        {
            log.info("Root Rejection Category Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("failed" ,"Data Update Failed");
        }
    }


}
