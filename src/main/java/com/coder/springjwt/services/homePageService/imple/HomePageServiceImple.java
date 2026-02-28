package com.coder.springjwt.services.homePageService.imple;

import com.coder.springjwt.dtos.customerPayloads.homepageDtos.ProductDetailsDataDto;
import com.coder.springjwt.dtos.customerPayloads.homepageDtos.SliderDto;
import com.coder.springjwt.dtos.customerPayloads.homepageDtos.productCategoryDto.HomePageSubCategoryDTO;
import com.coder.springjwt.dtos.customerPayloads.homepageDtos.productCategoryDto.HomePageTypeCategoryDTO;
import com.coder.springjwt.dtos.customerPayloads.homepageDtos.productCategoryDto.HomePageVariantCategoryDTO;
import com.coder.springjwt.emuns.seller.ProductStatus;
import com.coder.springjwt.exception.adminException.DataNotFoundException;
import com.coder.springjwt.models.adminModels.categories.SubCategoryModel;
import com.coder.springjwt.models.adminModels.categories.TypeCategoryModel;
import com.coder.springjwt.models.adminModels.categories.VariantCategoryModel;
import com.coder.springjwt.models.adminModels.slidersModels.SliderModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductFiles;
import com.coder.springjwt.models.sellerModels.productModels.ProductSizeRows;
import com.coder.springjwt.repository.adminRepository.categories.SubCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.productDetailsRepository.ProductDetailsRepo;
import com.coder.springjwt.repository.sliderRepository.SliderRepository;
import com.coder.springjwt.services.homePageService.HomePageService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class HomePageServiceImple implements HomePageService {

    @Autowired
    private SliderRepository sliderRepository;

    @Autowired
    private ProductDetailsRepo productDetailsRepo;

    @Autowired
    private SubCategoryRepo subCategoryRepo;


    @Override
    public ResponseEntity<?> fetchHomePage() {
        Map<Object,Object> mapNode = new HashMap<>();
        try {

            //CATEGORY DATA
            Map<String, Object> categoryData = this.getCategoryData();

            //SLIDER DATA
            List<SliderDto> sliderDtoList = new ArrayList<>();
            List<SliderModel> sliders = this.sliderRepository.findBySliderCategoryAndIsActive("HOMEPAGE_SLIDER" , Boolean.TRUE);
            if(sliders.isEmpty()) {
                throw new DataNotFoundException("No slider found | Please check slider Data");
            }

            for(SliderModel sm : sliders)
            {
                SliderDto sliderDto = new SliderDto();
                sliderDto.setTitle(sm.getTitle());
                sliderDto.setSubTitle(sm.getSubTitle());
                sliderDto.setDescription(sm.getDescription());
                sliderDto.setFileUrl(sm.getFileUrl());
                sliderDto.setRoutingLink(sm.getRoutingLink());
                sliderDtoList.add(sliderDto);
            }
            //SLIDER DATA END
            //######################################################################################################

            //PRODUCT DETAILS DATA
            List<ProductDetailsDataDto> productDetailsList = new ArrayList<>();
            List<ProductDetailsModel> productData = this.productDetailsRepo.
                                                    findTop100ByProductStatus(ProductStatus.APPROVED.toString(),
                                                    Sort.by("id").descending());

            for(ProductDetailsModel pdm : productData)
            {
                ProductDetailsDataDto productDetailsDataDto = new ProductDetailsDataDto();
                productDetailsDataDto.setProductId(pdm.getId());
                productDetailsDataDto.setProductName(pdm.getProductName());
                productDetailsDataDto.setDefaultName(pdm.getDefaultName());
                productDetailsDataDto.setManufacturerName(pdm.getManufacturerName());
                productDetailsDataDto.setBrand(pdm.getBrand());
                productDetailsDataDto.setProductDate(pdm.getProductDate());
                productDetailsDataDto.setProductTime(pdm.getProductTime());
                productDetailsDataDto.setProductStatus(pdm.getProductStatus());
                productDetailsDataDto.setPattern(pdm.getPattern());
                productDetailsDataDto.setMrp(pdm.getProductMrp());


                //Product Size Rows
                ProductSizeRows productSizeRows = pdm.getProductSizeRows().get(0);
                productDetailsDataDto.setInventory(productSizeRows.getInventory());
                productDetailsDataDto.setProductDiscount(productSizeRows.getProductDiscount());



                //Product Price With Shipping Charges
                BigDecimal productPrice = new BigDecimal(pdm.getProductPrice());
                BigDecimal shippingCharge = new BigDecimal(productSizeRows.getShippingCharges());
                BigDecimal finalPriceWithShipping = productPrice.add(shippingCharge);
                BigDecimal finalWithoutDecimal = finalPriceWithShipping.setScale(0, RoundingMode.HALF_UP);
                productDetailsDataDto.setPrice(finalWithoutDecimal.toString());



                //Product Files
                ProductFiles productFiles = pdm.getProductFiles().get(0);
                String fileUrl =  productFiles.getFileUrl();
                String fileName = productFiles.getFileName();
                if(fileUrl != null && fileName != null){
                    productDetailsDataDto.setProductMainFileUrl(fileUrl);
                    productDetailsDataDto.setFileName(fileName);
                }


                productDetailsList.add(productDetailsDataDto);
            }
            //PRODUCT DETAILS DATA END
            //######################################################################################################


            mapNode.put("sliderDtoList",sliderDtoList);
            mapNode.put("productDetailsList",productDetailsList);
            mapNode.put("categoryDataNode",categoryData);

            return ResponseGenerator.generateSuccessResponse(mapNode,"SUCCESS");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse();
        }
    }



    public Map<String, Object> getCategoryData() {
        try {
            Map<String, Object> response = new HashMap<>();

            List<SubCategoryModel> subcategoryData =
                    subCategoryRepo.findByCategoryNameIn(
                            List.of("Mobiles & Tablets", "Laptops & Computers" , "Men's Clothing" , "Footwear")
                    );

            List<HomePageSubCategoryDTO> subCategoryDTOList = new ArrayList<>();

            for (SubCategoryModel scm : subcategoryData) {

                HomePageSubCategoryDTO subDTO = new HomePageSubCategoryDTO();
                subDTO.setSubCategoryName(scm.getCategoryName());

                List<HomePageTypeCategoryDTO> typeDTOList = new ArrayList<>();

                for (TypeCategoryModel tcm : scm.getTypeCategoryModels()) {

                    HomePageTypeCategoryDTO typeDTO = new HomePageTypeCategoryDTO();
                    typeDTO.setTypeCategoryName(tcm.getCategoryName());

                    List<HomePageVariantCategoryDTO> variantDTOList = new ArrayList<>();

                    for (VariantCategoryModel vcm : tcm.getVariantCategoryModels()) {

                        HomePageVariantCategoryDTO variantDTO = new HomePageVariantCategoryDTO();
                        variantDTO.setVariantCategoryName(vcm.getCategoryName());

                        variantDTOList.add(variantDTO);
                    }

                    typeDTO.setVariants(variantDTOList);
                    typeDTOList.add(typeDTO);
                }

                subDTO.setTypes(typeDTOList);
                subCategoryDTOList.add(subDTO);
            }

            response.put("categoryData", subCategoryDTOList);
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }








}
