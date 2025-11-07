package com.coder.springjwt.services.adminServices.ProductRejectionReasonService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.productRejectionReasonDto.ProductRejectionReasonDto;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.ProductRejectionReasonModel.ProductRejectionReason;
import com.coder.springjwt.models.adminModels.ProductRejectionReasonModel.RootRejectionCategory;
import com.coder.springjwt.repository.productRejectionReasonRepo.ProductRejectionReasonRepo;
import com.coder.springjwt.repository.productRejectionReasonRepo.RootRejectionCategoryRepo;
import com.coder.springjwt.services.adminServices.ProductRejectionReasonService.ProductRejectionReasonService;
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
public class ProductRejectionReasonServiceImple implements ProductRejectionReasonService {

    @Autowired
    private ProductRejectionReasonRepo rejectionReasonRepo;

    @Autowired
    private RootRejectionCategoryRepo rootRejectionCategoryRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ResponseEntity<?> createRejectionReason(ProductRejectionReasonDto productRejectionReasonDto) {
        MessageResponse response =new MessageResponse();
        try {
            ProductRejectionReason productRejectionReason =  modelMapper.map(productRejectionReasonDto, ProductRejectionReason.class);
            log.info("Object Mapper Convert Success");

            RootRejectionCategory rootRejectionCategory = this.rootRejectionCategoryRepo.findByRootRejectionCategory(productRejectionReasonDto.getRootRejectionCategory())
                    .orElseThrow(() -> new RuntimeException("Root Rejection Category Not Found | Please Check..."));

            //Set Root Rejection Category To  Rejection Reason
            productRejectionReason.setRootRejectionCategories(rootRejectionCategory);

            //Rejection Reason Save
            this.rejectionReasonRepo.save(productRejectionReason);

            response.setMessage("Rejection Reason Saved Success");
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response, SellerMessageResponse.SUCCESS);

        }
        catch (DataIntegrityViolationException ex) {
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
    public ResponseEntity<?> getRejectionReasons() {
        try {
            List<ProductRejectionReason> rejectionReasonList =  this.rejectionReasonRepo.findAll();
            List<ProductRejectionReasonDto> rejectionReasonData = rejectionReasonList.stream()
                    .map(data -> modelMapper.map(data, ProductRejectionReasonDto.class))
                    .collect(Collectors.toList());
            return ResponseGenerator.generateSuccessResponse(rejectionReasonData,"SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Failed");
        }
    }

    @Override
    public ResponseEntity<?> deleteRejectionReason(long rejectionId) {
        try {
            ProductRejectionReason productRejectionReason = this.rejectionReasonRepo.findById(rejectionId).orElseThrow(
                    () -> new DataNotFoundException("Rejection id not Found"));
            this.rejectionReasonRepo.delete(productRejectionReason);
            log.info("Delete Rejection Reason Success => Rejection ID :: " + rejectionId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Rejection Reason Could Not deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("Rejection Reason Could not deleted :: " + e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> getRejectionReasonById(long rejectionId) {
        try {
            ProductRejectionReason productRejectionReason = this.rejectionReasonRepo.findById(rejectionId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            ProductRejectionReasonDto productRejectionReasonDto = modelMapper.map(productRejectionReason, ProductRejectionReasonDto.class);
            log.info("Product Rejection Reason Fetch Success");
            return ResponseGenerator.generateSuccessResponse(productRejectionReasonDto , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> updateRejectionReason(ProductRejectionReasonDto productRejectionReasonDto) {
        try {
            log.info(productRejectionReasonDto.toString());

            //get rejection Reason Data
            this.rejectionReasonRepo.findById(productRejectionReasonDto.getId())
                    .orElseThrow(() -> new DataNotFoundException("Variant Id not Found"));

//            Convert DTO TO Model Class
            ProductRejectionReason  productRejectionReason=  modelMapper.map(productRejectionReasonDto , ProductRejectionReason.class);
            this.rejectionReasonRepo.save(productRejectionReason);
            log.info("Rejection Reason Update Success");
            return ResponseGenerator.generateSuccessResponse("Success" , "Data update Success");

        }
        catch (Exception e)
        {
            log.info("Rejection Reason Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("failed" ," Data Update Failed");
        }
    }

    @Override
    public ResponseEntity<?> findByRootRejectionCategory(long rejectionId) {
        try {
            List<ProductRejectionReason> productRejectionReasons = this.rejectionReasonRepo.findByRootRejectionCategoriesId(rejectionId);
            log.info("findByRootRejectionCategory Data Fetch Success");
            return ResponseGenerator.generateSuccessResponse(productRejectionReasons , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Could Not fetch Root Rejection Category");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , "Error");
        }
    }

}
