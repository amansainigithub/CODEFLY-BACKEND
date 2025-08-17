package com.coder.springjwt.services.sellerServices.sellerFormBuilderService.imple;

import com.coder.springjwt.controllers.seller.formBuilderController.FormBuilderTool;
import com.coder.springjwt.controllers.seller.formBuilderController.FormRootCapture;
import com.coder.springjwt.services.sellerServices.sellerFormBuilderService.SellerFormBuilderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SellerFormBuilderServiceImple  implements SellerFormBuilderService {
    @Override
    public FormRootCapture getFormBuilder() {
        try {

            FormBuilderTool productName = new FormBuilderTool();
            productName.setId("01");
            productName.setIdentifier("productName");
            productName.setName("productName");
            productName.setType("TEXT");
            productName.setRequired(true);
            productName.setExclamationDesc("Product Name Examination");
            productName.setMinLength("3");
            productName.setMaxLength("10");
            productName.setPattern("");
            productName.setDescription("Product Name Desc");
            productName.setIsFieldDisabled("");

            FormBuilderTool defaultName = new FormBuilderTool();
            defaultName.setId("01");
            defaultName.setIdentifier("defaultName");
            defaultName.setName("defaultName");
            defaultName.setType("TEXT");
            defaultName.setRequired(false);
            defaultName.setExclamationDesc("defaultName Name Examination");
            defaultName.setMinLength("");
            defaultName.setMaxLength("");
            defaultName.setPattern("");
            defaultName.setDescription("Default Name Desc");
            defaultName.setIsFieldDisabled("");

            FormBuilderTool mrpPrice = new FormBuilderTool();
            mrpPrice.setId("01");
            mrpPrice.setIdentifier("mrpPrice");
            mrpPrice.setName("mrpPrice");
            mrpPrice.setType("NUMBER");
            mrpPrice.setRequired(true);
            mrpPrice.setExclamationDesc("mrpPrice Name Examination");
            mrpPrice.setMinLength("2");
            mrpPrice.setMaxLength("10");
            mrpPrice.setPattern("");
            mrpPrice.setDescription("mrpPrice Price Desc");
            mrpPrice.setIsFieldDisabled("");

            FormBuilderTool sellingPrice = new FormBuilderTool();
            sellingPrice.setId("01");
            sellingPrice.setIdentifier("sellingPrice");
            sellingPrice.setName("sellingPrice");
            sellingPrice.setType("NUMBER");
            sellingPrice.setRequired(true);
            sellingPrice.setExclamationDesc("sellingPrice Name Examination");
            sellingPrice.setMinLength("2");
            sellingPrice.setMaxLength("10");
            sellingPrice.setPattern("");
            sellingPrice.setDescription("sellingPrice Price Desc");
            sellingPrice.setIsFieldDisabled("");


            FormBuilderTool nameList = new FormBuilderTool();
            nameList.setId("01");
            nameList.setIdentifier("nameList");
            nameList.setName("nameList");
            nameList.setType("DROPDOWN");
            nameList.setRequired(true);
            nameList.setExclamationDesc("nameList Name Examination");
            nameList.setMinLength("");
            nameList.setMaxLength("");
            nameList.setPattern("");
            nameList.setDescription("nameList Price Desc");
            nameList.setIsFieldDisabled("");
            List<String> namesList = new ArrayList<>();
            namesList.add("Ishu saini");
            namesList.add("Neha kumari");
            namesList.add("Vimal sharma");
            namesList.add("Nitin gupta");
            namesList.add("Rahul singhal");
            namesList.add("Ajay Thakur");
            nameList.setValues(namesList);


            FormBuilderTool brandNames = new FormBuilderTool();
            brandNames.setId("01");
            brandNames.setIdentifier("brandNames");
            brandNames.setName("brandNames");
            brandNames.setType("DROPDOWN-MUL");
            brandNames.setRequired(true);
            brandNames.setExclamationDesc("brandNames Name Examination");
            brandNames.setMinLength("");
            brandNames.setMaxLength("");
            brandNames.setPattern("");
            brandNames.setDescription("brandNames Price Desc");
            brandNames.setIsFieldDisabled("");
            List<String> brandList = new ArrayList<>();
            brandList.add("Coca-Cola");
            brandList.add("Pepsi");
            brandList.add("Sprite");
            brandList.add("Fanta");
            brandList.add("Mountain Dew");
            brandNames.setValues(brandList);




            //ROWS STARTING
            FormBuilderTool fiberName = new FormBuilderTool();
            fiberName.setId("01");
            fiberName.setIdentifier("fiberName");
            fiberName.setName("fiberName");
            fiberName.setType("TEXT");
            fiberName.setRequired(true);
            fiberName.setExclamationDesc("fiber Name Examination");
            fiberName.setMinLength("3");
            fiberName.setMaxLength("10");
            fiberName.setPattern("");
            fiberName.setDescription("fiber Name Desc");
            fiberName.setIsFieldDisabled("");

            FormBuilderTool batteryList = new FormBuilderTool();
            batteryList.setId("01");
            batteryList.setIdentifier("batteryList");
            batteryList.setName("batteryList");
            batteryList.setType("DROPDOWN");
            batteryList.setRequired(true);
            batteryList.setExclamationDesc("batteryList Examination");
            batteryList.setMinLength("");
            batteryList.setMaxLength("");
            batteryList.setPattern("");
            batteryList.setDescription("batteryList Desc");
            batteryList.setIsFieldDisabled("");
            List<String> batteryNames = new ArrayList<>();
            batteryNames.add("Eveready");
            batteryNames.add("hippo");
            batteryNames.add("tesla");
            batteryNames.add("venom");
            batteryNames.add("naptaal");
            batteryList.setValues(batteryNames);

            List<FormBuilderTool> rows = new ArrayList<>();
            rows.add(fiberName);
            rows.add(batteryList);
            //ROWS ENDING

            List<FormBuilderTool> listOfInventory = new ArrayList<>();
            listOfInventory.add(productName);
            listOfInventory.add(defaultName);
            listOfInventory.add(mrpPrice);
            listOfInventory.add(sellingPrice);
            listOfInventory.add(nameList);
            listOfInventory.add(brandNames);

            FormRootCapture formRootCapture = new FormRootCapture();
            formRootCapture.setInventoryData(listOfInventory);
            formRootCapture.setRows(rows);

            return formRootCapture;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
