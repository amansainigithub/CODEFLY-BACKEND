package com.coder.springjwt.services.adminServices.chargeConfigService.chargeConfigImple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.chargeConfigDtos.ChargeConfigDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.categories.VariantCategoryModel;
import com.coder.springjwt.models.adminModels.chargeConfigModels.ChargeConfig;
import com.coder.springjwt.repository.adminRepository.categories.VariantCategoryRepo;
import com.coder.springjwt.repository.adminRepository.chargeConfigRepo.ChargeConfigRepo;
import com.coder.springjwt.services.adminServices.chargeConfigService.ChargeConfigService;
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
public class ChargeConfigServiceImple implements ChargeConfigService {

    @Autowired
    private ChargeConfigRepo chargeConfigRepo;

    @Autowired
    private VariantCategoryRepo variantCategoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> saveChargeConfig(ChargeConfigDto chargeConfigDto) {
        MessageResponse response =new MessageResponse();
        try {

            VariantCategoryModel variantData = this.variantCategoryRepo.
                                         findById(Long.parseLong(chargeConfigDto.getVariantId()))
                                        .orElseThrow(() -> new DataNotFoundException("Variant Category Not Found ID :: " + chargeConfigDto.getVariantId()));

            chargeConfigDto.setVariantName(variantData.getCategoryName());
            ChargeConfig chargeConfig =  modelMapper.map(chargeConfigDto, ChargeConfig.class);
            log.info("Object Mapper Convert Success");

            //save Charge Config
            this.chargeConfigRepo.save(chargeConfig);

            response.setMessage("Charge-Config Saved Success");
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
    public ResponseEntity<?> getChargeConfigList() {
        try {
            List<ChargeConfig> configList =  this.chargeConfigRepo.findAll();
            List<ChargeConfigDto> chargeConfigData = configList.stream()
                                                    .map(category -> modelMapper.map(category, ChargeConfigDto.class))
                                                    .collect(Collectors.toList());
            return ResponseGenerator.generateSuccessResponse(chargeConfigData,"Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Failed");
        }
    }

    @Override
    public ResponseEntity<?> deleteChargeConfig(long chargeId) {
        try {
            ChargeConfig chargeConfig = this.chargeConfigRepo.findById(chargeId).orElseThrow(
                    () -> new CategoryNotFoundException("Charge id not Found"));
            this.chargeConfigRepo.delete(chargeConfig);
            log.info("Delete Charge Success => Charge ID :: " + chargeId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Charge Could Not deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("Charge Could not deleted :: " + e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> getChargeConfigById(long chargeId) {
        try {
            ChargeConfig chargeConfig = this.chargeConfigRepo.findById(chargeId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            ChargeConfigDto chargeConfigDto = modelMapper.map(chargeConfig, ChargeConfigDto.class);
            log.info("Charge Config Data Fetch Success");
            return ResponseGenerator.generateSuccessResponse(chargeConfigDto , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> updateChargeConfig(ChargeConfigDto chargeConfigDto) {
        try {
            log.info(chargeConfigDto.toString());

            //get Born Data
            VariantCategoryModel variantData = this.variantCategoryRepo.findById(Long.parseLong(chargeConfigDto.getVariantId())).
                    orElseThrow(() -> new DataNotFoundException("Variant Id not Found"));

//            Convert DTO TO Model Class
            ChargeConfig chargeConfig =  modelMapper.map(chargeConfigDto , ChargeConfig.class);
            chargeConfig.setVariantName(variantData.getCategoryName());
            this.chargeConfigRepo.save(chargeConfig);

            log.info("Charge Update Success");
            return ResponseGenerator.generateSuccessResponse("Success" , "Data update Success");

        }
        catch (Exception e)
        {
            log.info("Charge Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("failed" ," Data Update Failed");
        }
    }
}
