package com.coder.springjwt.services.adminServices.slidersService.homepageSliderService.imple;

import com.coder.springjwt.buckets.filesBucket.bucketModels.BucketModel;
import com.coder.springjwt.buckets.filesBucket.bucketService.BucketService;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.SubCategoryDto;
import com.coder.springjwt.dtos.adminDtos.slidersDtos.SliderDtos.SliderDto;
import com.coder.springjwt.exception.adminException.CategoryNotFoundException;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.categories.SubCategoryModel;
import com.coder.springjwt.models.adminModels.slidersModels.SliderModel;
import com.coder.springjwt.repository.sliderRepository.SliderRepository;
import com.coder.springjwt.services.adminServices.slidersService.homepageSliderService.SliderService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SliderServiceImple implements SliderService {


    @Autowired
    private SliderRepository sliderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BucketService bucketService;


    @Override
    public ResponseEntity<?> createSlider(SliderDto sliderDto) {
        MessageResponse response =new MessageResponse();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            sliderDto.setUsername(auth.getName());

            //Convert DTO TO Model Class...
            SliderModel sliderModel =  modelMapper.map(sliderDto , SliderModel.class);
            log.info("Slider Model mapper Conversion Success");

            //save Slider
            this.sliderRepository.save(sliderModel);

            response.setMessage("Slider Saved Success");
            response.setStatus(HttpStatus.OK);
            return ResponseGenerator.generateSuccessResponse(response ,"Success");
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
    public ResponseEntity<?> getSlidersList() {
        try {
            List<SliderModel> sliderList =  this.sliderRepository.findAll();
            List<SliderDto> sliderDtos =   sliderList
                    .stream()
                    .map(slider -> modelMapper.map(slider, SliderDto.class))
                    .collect(Collectors.toList());
            return ResponseGenerator.generateSuccessResponse(sliderDtos,"Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Failed");
        }
    }

    @Override
    public ResponseEntity<?> getSliderById(long sliderId) {
        try {
            SliderModel sliderModel = this.sliderRepository.findById(sliderId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            SliderDto sliderDto = modelMapper.map(sliderModel, SliderDto.class);
            log.info("Sub Category Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(sliderDto , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> updateSlider(SliderDto sliderDto) {
        try {
            log.info(sliderDto.toString());
            this.sliderRepository.findById(sliderDto.getId()).orElseThrow(()->new DataNotFoundException("Data not Found"));

            SliderModel sliderModel =  modelMapper.map(sliderDto , SliderModel.class);
            this.sliderRepository.save(sliderModel);

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
    public ResponseEntity<?> deleteSliderById(long sliderId) {
        try {
            SliderModel data = this.sliderRepository.findById(sliderId).orElseThrow(
                    () -> new CategoryNotFoundException("Slider id not Found"));

            this.sliderRepository.deleteById(data.getId());
            log.info("Delete Success => Slider id :: " + sliderId );
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
    public ResponseEntity<?> updateSliderFile(MultipartFile file, Long sliderId) {
        try {
            log.info("Slider File Update...");
            SliderModel sliderModel = this.sliderRepository.findById(sliderId)
                    .orElseThrow(()-> new DataNotFoundException("DATA_NOT_FOUND"));

            //Delete old File
            bucketService.deleteFile(sliderModel.getFileUrl());

            //upload New File
            BucketModel bucketModel = bucketService.uploadFile(file);
            if(bucketModel != null)
            {
                sliderModel.setFileUrl(bucketModel.getBucketUrl());
                this.sliderRepository.save(sliderModel);
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
