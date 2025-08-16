package com.coder.springjwt.services.sellerServices.sellerStoreService.imple;

import com.coder.springjwt.formBuilderTools.FormBuilderModel.FormBuilderTool;
import com.coder.springjwt.formBuilderTools.formVariableKeys.FormBuilderRoot;
import com.coder.springjwt.models.adminModels.catalog.hsn.HsnCodes;
import com.coder.springjwt.repository.adminRepository.catalogRepos.HsnRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FormBuilderService {

    @Autowired
    private HsnRepository hsnRepository;

    public FormBuilderRoot getFormBuilder(){
        List<HsnCodes> hsnCodes = hsnRepository.findAll();

        FormBuilderTool productIdentity = new FormBuilderTool();
        productIdentity.setIdentifier("productName");
        productIdentity.setName("Product Name");
        productIdentity.setType("TEXT");
        productIdentity.setRequired(true);
        productIdentity.setDescription("Please Enter Product Name");
        productIdentity.setMinLength("10");
        productIdentity.setMaxLength("200");
        productIdentity.setExclamationDesc("Please Enter Product Name");
        productIdentity.setIsFiledDisabled("");

        FormBuilderTool gstField = new FormBuilderTool();
        gstField.setIdentifier("gst");
        gstField.setName("GST");
        gstField.setType("DROPDOWN");
        gstField.setRequired(true);
        gstField.setDescription("Gst Mandatory");
        gstField.setExclamationDesc("Gst Mandatory");
        gstField.setIsFiledDisabled("");
        gstField.setValues(List.of("5 %","10","12 %","15 %","18 %"));


        FormBuilderTool hsnField = new FormBuilderTool();
        hsnField.setIdentifier("hsn");
        hsnField.setName("HSN");
        hsnField.setType("DROPDOWN");
        hsnField.setRequired(true);
        hsnField.setDescription("hsn");
        hsnField.setExclamationDesc("India's Goods and Services Tax (GST), HSN stands for Harmonized System of Nomenclature. It is a globa");
        hsnField.setValues(hsnCodes.stream().map(HsnCodes::getHsn).collect(Collectors.toList()));

        FormBuilderTool productWeight = new FormBuilderTool();
        productWeight.setIdentifier("productWeight");
        productWeight.setName("Weight(g)");
        productWeight.setType("TEXT");
        productWeight.setRequired(true);
        productWeight.setDescription("Please Enter Net Weight");
        productWeight.setMinLength("1");
        productWeight.setMaxLength("5");
        productWeight.setExclamationDesc("Please Enter Net Weight");
        productWeight.setIsFiledDisabled("");

        List<FormBuilderTool> productIdentityList = new ArrayList<>();
        productIdentityList.add(productIdentity);
        productIdentityList.add(gstField);
        productIdentityList.add(hsnField);
        productIdentityList.add(productWeight);

//        =========================================

        //Sizes
        FormBuilderTool sizeField = new FormBuilderTool();
        sizeField.setIdentifier("productSize");
        sizeField.setName("Product Size");
        sizeField.setType("MULTISELECT");
        sizeField.setRequired(true);
        sizeField.setDescription("Product Size Mandatory");
        sizeField.setExclamationDesc("Product Size");
        sizeField.setIsFiledDisabled("");
        sizeField.setValues(List.of("S","M","L","XL","XXL","3XL","4XL","5XL","6XL"));

        List<FormBuilderTool> productSizeList = new ArrayList<>();
        productSizeList.add(sizeField);


//        =======================TABLE ROWS===============
        FormBuilderTool sizeLabel = new FormBuilderTool();
        sizeLabel.setIdentifier("productLabel");
        sizeLabel.setName("size");
        sizeLabel.setType("LABEL");
        sizeLabel.setRequired(true);
        sizeLabel.setDescription("product Size");
        sizeLabel.setExclamationDesc("product Size");


        FormBuilderTool productPrice = new FormBuilderTool();
        productPrice.setIdentifier("productPrice");
        productPrice.setName("Price");
        productPrice.setType("TEXT");
        productPrice.setRequired(true);
        productPrice.setDescription("Please Enter Price");
        productPrice.setMinLength("2");
        productPrice.setMaxLength("5");
        productPrice.setExclamationDesc("Price Alternatives");
        productPrice.setIsFiledDisabled("");

        FormBuilderTool productMrp = new FormBuilderTool();
        productMrp.setIdentifier("productMrp");
        productMrp.setName("Mrp");
        productMrp.setType("TEXT");
        productMrp.setRequired(true);
        productMrp.setDescription("product Mrp");
        productMrp.setMinLength("2");
        productMrp.setMaxLength("4");
        productMrp.setExclamationDesc("productMrp Alternatives");
        productMrp.setIsFiledDisabled("");

        FormBuilderTool productInventory = new FormBuilderTool();
        productInventory.setIdentifier("productInventory");
        productInventory.setName("Product Inventory");
        productInventory.setType("TEXT");
        productInventory.setRequired(true);
        productInventory.setDescription("Enter Product Inventory");
        productInventory.setMinLength("1");
        productInventory.setMaxLength("5");
        productInventory.setExclamationDesc("Product Inventory");
        productInventory.setIsFiledDisabled("");

        FormBuilderTool productLength = new FormBuilderTool();
        productLength.setIdentifier("productLength");
        productLength.setName("Product Length(cm)");
        productLength.setType("DROPDOWN");
        productLength.setRequired(true);
        productLength.setDescription("Enter Product Length");
        productLength.setExclamationDesc("Product Length");
        productLength.setIsFiledDisabled("");
        productLength.setValues(List.of("5","10","15","20","25","30","35","40","45","50"));

        FormBuilderTool waistSize = new FormBuilderTool();
        waistSize.setIdentifier("waistSize");
        waistSize.setName("Waist Size(cm)");
        waistSize.setType("DROPDOWN");
        waistSize.setRequired(true);
        waistSize.setDescription("Enter Waist Size");
        waistSize.setExclamationDesc("Waist Size");
        waistSize.setIsFiledDisabled("");
        waistSize.setValues(List.of("5","10","15","20","25","30","35","40","45","50"));

        FormBuilderTool shoulderWidth = new FormBuilderTool();
        shoulderWidth.setIdentifier("shoulderWidth");
        shoulderWidth.setName("Shoulder Width(cm)");
        shoulderWidth.setType("DROPDOWN");
        shoulderWidth.setRequired(true);
        shoulderWidth.setDescription("Enter Shoulder Width");
        shoulderWidth.setExclamationDesc("Shoulder Width");
        shoulderWidth.setIsFiledDisabled("");
        shoulderWidth.setValues(List.of("5","10","15","20","25","30","35","40","45","50"));

        FormBuilderTool chestBustSize = new FormBuilderTool();
        chestBustSize.setIdentifier("chestBustSize");
        chestBustSize.setName("Chest/Bust Size(cm)");
        chestBustSize.setType("DROPDOWN");
        chestBustSize.setRequired(true);
        chestBustSize.setDescription("Enter Chest/Bust Size");
        chestBustSize.setExclamationDesc("Chest/Bust Size");
        chestBustSize.setIsFiledDisabled("");
        chestBustSize.setValues(List.of("5","10","15","20","25","30","35","40","45","50"));

        FormBuilderTool skuId = new FormBuilderTool();
        skuId.setIdentifier("skuId");
        skuId.setName("SKU Code");
        skuId.setType("TEXT");
        skuId.setRequired(false);
        skuId.setDescription("skuId skuId");
        skuId.setMinLength("");
        skuId.setMaxLength("100");
        skuId.setExclamationDesc("skuId skuId");
        skuId.setIsFiledDisabled("");

        List<FormBuilderTool> productVariants = new ArrayList<>();
        productVariants.add(sizeLabel);
        productVariants.add(productPrice);
        productVariants.add(productMrp);
        productVariants.add(productInventory);
        productVariants.add(productLength);
        productVariants.add(waistSize);
        productVariants.add(shoulderWidth);
        productVariants.add(chestBustSize);
        productVariants.add(skuId);


        //////////////////Product Details///////////

        FormBuilderTool productCode = new FormBuilderTool();
        productCode.setIdentifier("productCode");
        productCode.setName("product code(optional)");
        productCode.setType("TEXT");
        productCode.setRequired(false);
        productCode.setDescription("Enter Product Code");
        productCode.setMinLength("10");
        productCode.setMaxLength("50");
        productCode.setExclamationDesc("productCode Verification");
        productCode.setIsFiledDisabled("");

        FormBuilderTool sleeveTypeField = new FormBuilderTool();
        sleeveTypeField.setIdentifier("sleeveType");
        sleeveTypeField.setName("Sleeve Type");
        sleeveTypeField.setType("DROPDOWN");
        sleeveTypeField.setRequired(true);
        sleeveTypeField.setDescription("Sleeve");
        sleeveTypeField.setMinLength("");
        sleeveTypeField.setMaxLength("");
        sleeveTypeField.setExclamationDesc("sleeveType");
        sleeveTypeField.setValues(List.of("Half Sleeve %","full Sleeve"));

        FormBuilderTool fitTypeField = new FormBuilderTool();
        fitTypeField.setIdentifier("fitType");
        fitTypeField.setName("fitType Name");
        fitTypeField.setType("DROPDOWN");
        fitTypeField.setRequired(true);
        fitTypeField.setDescription("fitType please select");
        fitTypeField.setMinLength("");
        fitTypeField.setMaxLength("");
        fitTypeField.setExclamationDesc("fit Type");
        fitTypeField.setValues(List.of("Regular Fit","Skin Fit"));

        FormBuilderTool genderField = new FormBuilderTool();
        genderField.setIdentifier("gender");
        genderField.setName("gender");
        genderField.setType("DROPDOWN");
        genderField.setRequired(true);
        genderField.setDescription("Select Gender");
        genderField.setMinLength("");
        genderField.setMaxLength("");
        genderField.setExclamationDesc("gender");
        genderField.setValues(List.of("Male","Female","Other"));

        FormBuilderTool materialTypeField = new FormBuilderTool();
        materialTypeField.setIdentifier("materialType");
        materialTypeField.setName("material Type");
        materialTypeField.setType("DROPDOWN");
        materialTypeField.setRequired(true);
        materialTypeField.setDescription("Select Material Type");
        materialTypeField.setMinLength("");
        materialTypeField.setMaxLength("");
        materialTypeField.setExclamationDesc("material Type");
        materialTypeField.setValues(List.of("Cotton","Satin","Leather","Linen","Denim","Velvet","wool"));

        FormBuilderTool colorField = new FormBuilderTool();
        colorField.setIdentifier("productColor");
        colorField.setName("productColor");
        colorField.setType("DROPDOWN");
        colorField.setRequired(true);
        colorField.setDescription("Select Product Color");
        colorField.setMinLength("");
        colorField.setMaxLength("");
        colorField.setExclamationDesc("productColor");
        colorField.setValues(List.of("Yellow","Green","Blue","Green","Orange","Velvet","Brown"));

        FormBuilderTool countryOriginField = new FormBuilderTool();
        countryOriginField.setIdentifier("country");
        countryOriginField.setName("country");
        countryOriginField.setType("DROPDOWN");
        countryOriginField.setRequired(true);
        countryOriginField.setDescription("Select Country");
        countryOriginField.setMinLength("");
        countryOriginField.setMaxLength("");
        countryOriginField.setExclamationDesc("country");
        countryOriginField.setValues(List.of("India"));

        FormBuilderTool patternField = new FormBuilderTool();
        patternField.setIdentifier("pattern");
        patternField.setName("pattern");
        patternField.setType("DROPDOWN");
        patternField.setRequired(true);
        patternField.setDescription("Select Pattern");
        patternField.setMinLength("");
        patternField.setMaxLength("");
        patternField.setExclamationDesc("pattern");
        patternField.setValues(List.of("Line","Circle","shades","circle","herosim"));

        FormBuilderTool finishingType = new FormBuilderTool();
        finishingType.setIdentifier("finishingType");
        finishingType.setName("Finishing Type ");
        finishingType.setType("DROPDOWN");
        finishingType.setRequired(true);
        finishingType.setDescription("finishingType please select");
        finishingType.setMinLength("");
        finishingType.setMaxLength("");
        finishingType.setExclamationDesc("finishingType");
        finishingType.setValues(List.of("Liner","Rarer","Printing","blur shade","shades","multiShades"));


        FormBuilderTool netQuantity = new FormBuilderTool();
        netQuantity.setIdentifier("netQuantity");
        netQuantity.setName("Net Quantity");
        netQuantity.setType("DROPDOWN");
        netQuantity.setRequired(true);
        netQuantity.setDescription("Select Net Quantity");
        netQuantity.setMinLength("");
        netQuantity.setMaxLength("");
        netQuantity.setExclamationDesc("net Quantity");
        netQuantity.setValues(List.of("1","2","3","4","5","6","7","8","9","10"));


        List<FormBuilderTool> productDetails = new ArrayList<>();
        productDetails.add(productCode);
        productDetails.add(colorField);
        productDetails.add(sleeveTypeField);
        productDetails.add(fitTypeField);
        productDetails.add(genderField);
        productDetails.add(materialTypeField);
        productDetails.add(countryOriginField);
        productDetails.add(patternField);
        productDetails.add(finishingType);
        productDetails.add(netQuantity);


        //Product Description and Other Details
        FormBuilderTool manufactureField = new FormBuilderTool();
        manufactureField.setIdentifier("manufactureName");
        manufactureField.setName("manufactureName(Optional)");
        manufactureField.setType("TEXT");
        manufactureField.setRequired(false);
        manufactureField.setDescription("Select Manufacturer Name");
        manufactureField.setMinLength("10");
        manufactureField.setMaxLength("50");
        manufactureField.setExclamationDesc("manufactureName");
        manufactureField.setValues(null);


        FormBuilderTool brandField = new FormBuilderTool();
        brandField.setIdentifier("brandField");
        brandField.setName("Brand(Optional)");
        brandField.setType("DROPDOWN");
        brandField.setRequired(false);
        brandField.setDescription("brandField please select");
        brandField.setMinLength("");
        brandField.setMaxLength("");
        brandField.setExclamationDesc("Brand Field");
        brandField.setValues(List.of("Jack & jones","Microman","Puma","Generic","lux cozi","spyker"));

        FormBuilderTool descriptionFiled = new FormBuilderTool();
        descriptionFiled.setIdentifier("description");
        descriptionFiled.setName("Description");
        descriptionFiled.setType("TEXTBOX");
        descriptionFiled.setRequired(true);
        descriptionFiled.setDescription("please fill description");
        descriptionFiled.setMinLength("50");
        descriptionFiled.setMaxLength("5000");
        descriptionFiled.setExclamationDesc("Description");
        descriptionFiled.setValues(null);

        List<FormBuilderTool> productOtherDetails = new ArrayList<>();
        productOtherDetails.add(manufactureField);
        productOtherDetails.add(brandField);
        productOtherDetails.add(descriptionFiled);


        //AddVariant Creation Product Data (Model)
        List<FormBuilderTool> makerProductVariant = new ArrayList<>();
        makerProductVariant.add(colorField);
        makerProductVariant.add(sizeField);

        //Variant Creation Product Data (Model)
        FormBuilderTool makerColorVariant = new FormBuilderTool();
        makerColorVariant.setIdentifier("colorVariant");
        makerColorVariant.setName("Color");
        makerColorVariant.setType("LABEL");
        makerColorVariant.setRequired(true);
        makerColorVariant.setDescription("Color Variant");
        makerColorVariant.setMinLength("");
        makerColorVariant.setMaxLength("0");
        makerColorVariant.setExclamationDesc("Color Variant");
        makerColorVariant.setIsFiledDisabled("");

        List<FormBuilderTool> makerAddVariantData = new ArrayList<>();
        makerAddVariantData.add(makerColorVariant);
        makerAddVariantData.add(sizeLabel);
        makerAddVariantData.add(productPrice);
        makerAddVariantData.add(productMrp);
        makerAddVariantData.add(productInventory);
        makerAddVariantData.add(productLength);
        makerAddVariantData.add(waistSize);
        makerAddVariantData.add(shoulderWidth);
        makerAddVariantData.add(chestBustSize);
        makerAddVariantData.add(skuId);

        FormBuilderRoot formBuilderRoot = new FormBuilderRoot();
        formBuilderRoot.setProductIdentityList(productIdentityList);
        formBuilderRoot.setProductSizes(productSizeList);
        formBuilderRoot.setProductVariants(productVariants);
        formBuilderRoot.setProductDetails(productDetails);
        formBuilderRoot.setProductOtherDetails(productOtherDetails);

        //Maker Product Variant
        formBuilderRoot.setMakerColorAndSize(makerProductVariant);
        formBuilderRoot.setMakerAddVariantData(makerAddVariantData);

        JSONObject jsonObject = new JSONObject(formBuilderRoot);
        System.out.println(jsonObject);
        return  formBuilderRoot;
    }

}
