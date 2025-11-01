package com.coder.springjwt.services.sellerServices.engineXService.imple;

import com.coder.springjwt.controllers.seller.engineXController.FormBuilderTool;
import com.coder.springjwt.controllers.seller.engineXController.FormRootCapture;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class EngineXDummyData {


    public static String getDummyData(){

        FormBuilderTool productName = new FormBuilderTool();
        productName.setId("01");
        productName.setIdentifier("productName");
        productName.setName("Product Name");
        productName.setPlaceHolder("Enter Product Name");
        productName.setType("TEXT");
        productName.setRequired(true);
        productName.setExclamationDesc("Please enter the product name only. Avoid adding details like size, weight, price, or dimensions.");
        productName.setMinLength("5");
        productName.setMaxLength("500");
        productName.setPattern("");
        productName.setDescription("Please enter the product name only. Avoid adding details like size, weight, price, or dimensions.");
        productName.setIsFieldDisabled("");

        FormBuilderTool defaultName = new FormBuilderTool();
        defaultName.setId("02");
        defaultName.setIdentifier("defaultName");
        defaultName.setName("Default Name");
        defaultName.setPlaceHolder("Enter Default Name");
        defaultName.setType("TEXT");
        defaultName.setRequired(false);
        defaultName.setExclamationDesc("Provide a default name that will automatically appear for the product if another name is not set.");
        defaultName.setMinLength("");
        defaultName.setMaxLength("");
        defaultName.setPattern("");
        defaultName.setDescription("Provide a default name that will automatically appear for the product if another name is not set.");
        defaultName.setIsFieldDisabled("");

        FormBuilderTool gst = new FormBuilderTool();
        gst.setId("03");
        gst.setIdentifier("gst");
        gst.setName("GST");
        gst.setPlaceHolder("Enter GST");
        gst.setType("DROPDOWN");
        gst.setRequired(true);
        gst.setExclamationDesc("Specify the GST rate that applies to this product (in percentage).");
        gst.setMinLength("");
        gst.setMaxLength("");
        gst.setPattern("");
        gst.setDescription("Specify the GST rate that applies to this product (in percentage).");
        gst.setIsFieldDisabled("");
        List<String> gstPercentage = new ArrayList<>();
        gstPercentage.add("5");
        gstPercentage.add("7");
        gstPercentage.add("10");
        gstPercentage.add("12");
        gstPercentage.add("15");
        gstPercentage.add("18");
        gst.setValues(gstPercentage);

        FormBuilderTool hsnCode = new FormBuilderTool();
        hsnCode.setId("04");
        hsnCode.setIdentifier("hsnCode");
        hsnCode.setName("HSN Code");
        hsnCode.setPlaceHolder("Enter HSN");
        hsnCode.setType("DROPDOWN");
        hsnCode.setRequired(true);
        hsnCode.setExclamationDesc("HSN code is mandatory for GST filing. Enter the valid code as per product category.");
        hsnCode.setMinLength("");
        hsnCode.setMaxLength("");
        hsnCode.setPattern("");
        hsnCode.setDescription("HSN code is mandatory for GST filing. Enter the valid code as per product category.");
        hsnCode.setIsFieldDisabled("");
        List<String> hsnCodeList = new ArrayList<>();
        hsnCodeList.add("12031");
        hsnCodeList.add("15023");
        hsnCodeList.add("16035");
        hsnCodeList.add("65022");
        hsnCodeList.add("65033");
        hsnCodeList.add("44045");
        hsnCode.setValues(hsnCodeList);


        FormBuilderTool netWeight = new FormBuilderTool();
        netWeight.setId("05");
        netWeight.setIdentifier("netWeight");
        netWeight.setName("Net Weight");
        netWeight.setPlaceHolder("Enter Net Weight");
        netWeight.setType("NUMBER");
        netWeight.setRequired(true);
        netWeight.setExclamationDesc("Means the total weight of the product excluding any packaging weight. The acceptable unit is grams (g). For pre-packed products, this information is available on the packaging label. If you are not listing a pre-packed product, please measure the weight of the item excluding any external packaging and provide the value in SI unit (grams).");
        netWeight.setMin("10");
        netWeight.setMax("9999");
        netWeight.setPattern("");
        netWeight.setDescription("Means the total weight of the product excluding any packaging weight. The acceptable unit is grams (g). For pre-packed products, this information is available on the packaging label. If you are not listing a pre-packed product, please measure the weight of the item excluding any external packaging and provide the value in SI unit (grams).");
        netWeight.setIsFieldDisabled("");


        FormBuilderTool sizes = new FormBuilderTool();
        sizes.setId("06");
        sizes.setIdentifier("productSizes");
        sizes.setName("Product Sizes");
        sizes.setType("DROPDOWN-MUL");
        sizes.setRequired(true);
        sizes.setExclamationDesc("productSizes Name Examination");
        sizes.setMinLength("");
        sizes.setMaxLength("");
        sizes.setPattern("");
        sizes.setDescription("productSizes Price Desc");
        sizes.setIsFieldDisabled("");
        List<String> sizeList = new ArrayList<>();
        sizeList.add("XXS");
        sizeList.add("XS");
        sizeList.add("S");
        sizeList.add("M");
        sizeList.add("L");
        sizeList.add("XL");
        sizeList.add("XXL");
        sizeList.add("XXXL");
        sizeList.add("4XL");
        sizeList.add("5XL");
        sizeList.add("6XL");
        sizeList.add("7XL");
        sizeList.add("8XL");
        sizeList.add("9XL");
        sizeList.add("10XL");
        sizeList.add("LX");
        sizeList.add("SM");
        sizeList.add("Free Size");
        sizes.setValues(sizeList);


        List<FormBuilderTool> listOfInventory = new ArrayList<>();
        listOfInventory.add(productName);
        listOfInventory.add(defaultName);
        listOfInventory.add(gst);
        listOfInventory.add(hsnCode);
        listOfInventory.add(netWeight);
        listOfInventory.add(sizes);


        //ROWS STARTING
        FormBuilderTool mrp = new FormBuilderTool();
        mrp.setId("08");
        mrp.setIdentifier("mrp");
        mrp.setName("MRP");
        mrp.setPlaceHolder("Enter MRP");
        mrp.setType("NUMBER");
        mrp.setRequired(true);
        mrp.setExclamationDesc("This is the Maximum Retail Price (MRP) printed on the product. It must be higher than your selling price.");
        mrp.setMin("10");
        mrp.setMax("10000");
        mrp.setPattern("");
        mrp.setDescription("This is the Maximum Retail Price (MRP) printed on the product. It must be higher than your selling price.");
        mrp.setIsFieldDisabled("");

        FormBuilderTool price = new FormBuilderTool();
        price.setId("07");
        price.setIdentifier("price");
        price.setName("selling price");
        price.setPlaceHolder("Enter Price");
        price.setType("NUMBER");
        price.setRequired(true);
        price.setExclamationDesc("This is the standard price at which you sell your product. It should always be less than the MRP.");
        price.setMin("10");
        price.setMax("10000");
        price.setPattern("");
        price.setDescription("This is the standard price at which you sell your product. It should always be less than the MRP.");
        price.setIsFieldDisabled("");

        FormBuilderTool inventory = new FormBuilderTool();
        inventory.setId("09");
        inventory.setIdentifier("inventory");
        inventory.setName("Inventory");
        inventory.setPlaceHolder("Enter inventory");
        inventory.setType("NUMBER");
        inventory.setRequired(true);
        inventory.setExclamationDesc("Inventory defines the detailed stock information of a product such as its size, pricing, and other attributes. It helps in managing available product variants and ensures correct pricing and stock levels are maintained.");
        inventory.setMin("1");
        inventory.setMax("2000");
        inventory.setPattern("");
        inventory.setDescription("Inventory defines the detailed stock information of a product such as its size, pricing, and other attributes. It helps in managing available product variants and ensures correct pricing and stock levels are maintained.");
        inventory.setIsFieldDisabled("");

        FormBuilderTool skuCode = new FormBuilderTool();
        skuCode.setId("10");
        skuCode.setIdentifier("skuCode");
        skuCode.setName("SKU Code (Optional)");
        skuCode.setPlaceHolder("Enter SKU ID");
        skuCode.setType("TEXT");
        skuCode.setRequired(false);
        skuCode.setExclamationDesc("SKU ID is a unique code assigned to each product or product variation in inventory. It helps in tracking stock, managing sales, and differentiating between product variants like size, color, or brand.");
        skuCode.setMinLength("");
        skuCode.setMaxLength("12");
        skuCode.setPattern("");
        skuCode.setDescription("SKU ID is a unique code assigned to each product or product variation in inventory. It helps in tracking stock, managing sales, and differentiating between product variants like size, color, or brand.");
        skuCode.setIsFieldDisabled("");

        FormBuilderTool chestSize = new FormBuilderTool();
        chestSize.setId("11");
        chestSize.setIdentifier("chestSize");
        chestSize.setName("Chest Size (INCH)");
        chestSize.setPlaceHolder("Enter Chest Size");
        chestSize.setType("DROPDOWN");
        chestSize.setRequired(true);
        chestSize.setExclamationDesc("Chest Size refers to the garment’s measurement around the chest area. Please measure around the fullest part of your chest to select the right size.");
        chestSize.setMinLength("");
        chestSize.setMaxLength("");
        chestSize.setPattern("");
        chestSize.setDescription("Chest Size refers to the garment’s measurement around the chest area. Please measure around the fullest part of your chest to select the right size.");
        chestSize.setIsFieldDisabled("");
        List<String> chestSizeList = new ArrayList<>();
        chestSizeList.add("20");
        chestSizeList.add("22");
        chestSizeList.add("24");
        chestSizeList.add("28");
        chestSizeList.add("30");
        chestSizeList.add("32");
        chestSizeList.add("34");
        chestSizeList.add("36");
        chestSizeList.add("38");
        chestSizeList.add("40");
        chestSize.setValues(chestSizeList);

        FormBuilderTool lengthSize = new FormBuilderTool();
        lengthSize.setId("11");
        lengthSize.setIdentifier("lengthSize");
        lengthSize.setName("Length Size (INCH)");
        lengthSize.setPlaceHolder("Enter Length Size");
        lengthSize.setType("DROPDOWN");
        lengthSize.setRequired(true);
        lengthSize.setExclamationDesc("Length Size refers to the garment’s measurement around the chest area. Please measure around the fullest part of your chest to select the right size.");
        lengthSize.setMinLength("");
        lengthSize.setMaxLength("");
        lengthSize.setPattern("");
        lengthSize.setDescription("Length Size refers to the garment’s measurement around the chest area. Please measure around the fullest part of your chest to select the right size.");
        lengthSize.setIsFieldDisabled("");
        List<String> lengthSizeList = new ArrayList<>();
        lengthSizeList.add("20");
        lengthSizeList.add("21");
        lengthSizeList.add("22");
        lengthSizeList.add("23");
        lengthSizeList.add("24");
        lengthSizeList.add("25");
        lengthSizeList.add("25");
        lengthSizeList.add("27");
        lengthSizeList.add("28");
        lengthSizeList.add("29");
        lengthSizeList.add("30");
        lengthSize.setValues(lengthSizeList);

        FormBuilderTool shoulderSize = new FormBuilderTool();
        shoulderSize.setId("11");
        shoulderSize.setIdentifier("shoulderSize");
        shoulderSize.setName("Shoulder Size (INCH)");
        shoulderSize.setPlaceHolder("Enter Shoulder Size");
        shoulderSize.setType("DROPDOWN");
        shoulderSize.setRequired(true);
        shoulderSize.setExclamationDesc("Length Size refers to the garment’s measurement around the chest area. Please measure around the fullest part of your chest to select the right size.");
        shoulderSize.setMinLength("");
        shoulderSize.setMaxLength("");
        shoulderSize.setPattern("");
        shoulderSize.setDescription("Length Size refers to the garment’s measurement around the chest area. Please measure around the fullest part of your chest to select the right size.");
        shoulderSize.setIsFieldDisabled("");
        List<String> shoulderSizeList = new ArrayList<>();
        shoulderSizeList.add("20");
        shoulderSizeList.add("22");
        shoulderSizeList.add("24");
        shoulderSizeList.add("28");
        shoulderSizeList.add("30");
        shoulderSizeList.add("32");
        shoulderSizeList.add("34");
        shoulderSizeList.add("36");
        shoulderSizeList.add("38");
        shoulderSizeList.add("40");
        shoulderSizeList.add("42");
        shoulderSizeList.add("44");
        shoulderSizeList.add("46");
        shoulderSizeList.add("48");
        shoulderSize.setValues(shoulderSizeList);

        List<FormBuilderTool> rows = new ArrayList<>();
        rows.add(mrp);
        rows.add(price);
        rows.add(inventory);
        rows.add(skuCode);
        rows.add(chestSize);
        rows.add(lengthSize);
        rows.add(shoulderSize);
        //ROWS ENDING




        //PRODUCT DETAILS STARTING
        FormBuilderTool color = new FormBuilderTool();
        color.setId("12");
        color.setIdentifier("color");
        color.setName("color");
        color.setPlaceHolder("Enter Color");
        color.setType("DROPDOWN");
        color.setRequired(true);
        color.setExclamationDesc("Enter the main visible color of your product (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        color.setMinLength("");
        color.setMaxLength("");
        color.setPattern("");
        color.setDescription("Enter the main visible color of your product (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        color.setIsFieldDisabled("");
        List<String> colorList = new ArrayList<>();
        colorList.add("Crimson");
        colorList.add("RoyalBlue");
        colorList.add("SeaGreen");
        colorList.add("GoldenRod");
        colorList.add("SlateBlue");
        colorList.add("DeepPink");
        colorList.add("Teal");
        colorList.add("Ivory");
        color.setValues(colorList);

        FormBuilderTool netQuantity = new FormBuilderTool();
        netQuantity.setId("13");
        netQuantity.setIdentifier("netQuantity");
        netQuantity.setName("Net Quantity");
        netQuantity.setPlaceHolder("Enter Net Quantity");
        netQuantity.setType("DROPDOWN");
        netQuantity.setRequired(true);
        netQuantity.setExclamationDesc("Enter the main visible color of your product (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        netQuantity.setMinLength("");
        netQuantity.setMaxLength("");
        netQuantity.setPattern("");
        netQuantity.setDescription("Enter the main visible color of your product (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        netQuantity.setIsFieldDisabled("");
        List<String> netQuantityList = new ArrayList<>();
        netQuantityList.add("1");
        netQuantityList.add("2");
        netQuantityList.add("3");
        netQuantityList.add("4");
        netQuantityList.add("5");
        netQuantityList.add("6");
        netQuantityList.add("7");
        netQuantityList.add("8");
        netQuantityList.add("9");
        netQuantityList.add("10");
        netQuantity.setValues(netQuantityList);


        FormBuilderTool neck = new FormBuilderTool();
        neck.setId("13");
        neck.setIdentifier("neck");
        neck.setName("Neck");
        neck.setPlaceHolder("Enter Net Neck");
        neck.setType("DROPDOWN");
        neck.setRequired(true);
        neck.setExclamationDesc("Enter the main visible color of your product (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        neck.setMinLength("");
        neck.setMaxLength("");
        neck.setPattern("");
        neck.setDescription("Enter the main visible color of your product (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        neck.setIsFieldDisabled("");
        List<String> neckList = new ArrayList<>();
        neckList.add("Round Neck / Crew Neck");
        neckList.add("V-Neck");
        neckList.add("Polo Collar / Collar Neck");
        neckList.add("Henley Neck (Buttoned Round)");
        neckList.add("Scoop Neck");
        neckList.add("Boat Neck");
        neckList.add("Mandarin Collar / Chinese Collar");
        neckList.add("Hooded Neck");
        neckList.add("Turtle Neck / High Neck");
        neckList.add("Square Neck");
        neck.setValues(neckList);

        FormBuilderTool occasion = new FormBuilderTool();
        occasion.setId("13");
        occasion.setIdentifier("occasion");
        occasion.setName("Occasion");
        occasion.setPlaceHolder("Enter Occasion");
        occasion.setType("DROPDOWN");
        occasion.setRequired(true);
        occasion.setExclamationDesc("Enter the main visible color of your product (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        occasion.setMinLength("");
        occasion.setMaxLength("");
        occasion.setPattern("");
        occasion.setDescription("Enter the main visible color of your product (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        occasion.setIsFieldDisabled("");
        List<String> occasionList = new ArrayList<>();
        occasionList.add("Casual");
        occasionList.add("Formal");
        occasionList.add("Party");
        occasionList.add("Festive");
        occasionList.add("Sports / Activewear");
        occasionList.add("Office / Workwear");
        occasionList.add("Ethnic / Traditional");
        occasion.setValues(occasionList);


        FormBuilderTool pattern = new FormBuilderTool();
        pattern.setId("13");
        pattern.setIdentifier("pattern");
        pattern.setName("Pattern");
        pattern.setPlaceHolder("Enter Pattern");
        pattern.setType("DROPDOWN");
        pattern.setRequired(true);
        pattern.setExclamationDesc("Enter the main visible color of your product (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        pattern.setMinLength("");
        pattern.setMaxLength("");
        pattern.setPattern("");
        pattern.setDescription("Enter the main visible color of your product (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        pattern.setIsFieldDisabled("");
        List<String> patterList = new ArrayList<>();
        patterList.add("Solid / Plain");
        patterList.add("Striped");
        patterList.add("Checked / Checkered");
        patterList.add("Printed");
        patterList.add("Polka Dots");
        patterList.add("Floral");
        patterList.add("Geometric");
        pattern.setValues(patterList);


        FormBuilderTool sleeveLength = new FormBuilderTool();
        sleeveLength.setId("13");
        sleeveLength.setIdentifier("sleeveLength");
        sleeveLength.setName("Sleeve Length");
        sleeveLength.setPlaceHolder("Enter sleeveLength");
        sleeveLength.setType("DROPDOWN");
        sleeveLength.setRequired(true);
        sleeveLength.setExclamationDesc("Enter the main sleeveLength color of your product (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        sleeveLength.setMinLength("");
        sleeveLength.setMaxLength("");
        sleeveLength.setPattern("");
        sleeveLength.setDescription("Enter the main sleeveLength color of your product (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        sleeveLength.setIsFieldDisabled("");
        List<String> sleeveLengthList = new ArrayList<>();
        sleeveLengthList.add("Sleeveless");
        sleeveLengthList.add("Cap Sleeve");
        sleeveLengthList.add("Short Sleeve / Half Sleeve");
        sleeveLengthList.add("Elbow Length");
        sleeveLengthList.add("Three-Quarter Sleeve (3/4th)");
        sleeveLengthList.add("Full Sleeve / Long Sleeve");
        sleeveLengthList.add("Roll-up Sleeve");
        sleeveLength.setValues(sleeveLengthList);

        FormBuilderTool countryOfOrigin = new FormBuilderTool();
        countryOfOrigin.setId("13");
        countryOfOrigin.setIdentifier("countryOfOrigin");
        countryOfOrigin.setName("Country Origin");
        countryOfOrigin.setPlaceHolder("Enter Country of Origin");
        countryOfOrigin.setType("DROPDOWN");
        countryOfOrigin.setRequired(true);
        countryOfOrigin.setExclamationDesc("Enter the main visible color of your product (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        countryOfOrigin.setMinLength("");
        countryOfOrigin.setMaxLength("");
        countryOfOrigin.setPattern("");
        countryOfOrigin.setDescription("Enter the main visible color of your product (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        countryOfOrigin.setIsFieldDisabled("");
        List<String> countryOfOriginList = new ArrayList<>();
        countryOfOriginList.add("INDIA");
        countryOfOrigin.setValues(countryOfOriginList);


        FormBuilderTool manufacturerName = new FormBuilderTool();
        manufacturerName.setId("01");
        manufacturerName.setIdentifier("manufacturerName");
        manufacturerName.setName("Manufacturer Name");
        manufacturerName.setPlaceHolder("Enter Manufacturer Name");
        manufacturerName.setType("TEXT");
        manufacturerName.setRequired(true);
        manufacturerName.setExclamationDesc("Please enter the product name only. Avoid adding details like size, weight, price, or dimensions.");
        manufacturerName.setMinLength("3");
        manufacturerName.setMaxLength("100");
        manufacturerName.setPattern("");
        manufacturerName.setDescription("Please enter the product name only. Avoid adding details like size, weight, price, or dimensions.");
        manufacturerName.setIsFieldDisabled("");

        FormBuilderTool manufacturerAddress = new FormBuilderTool();
        manufacturerAddress.setId("01");
        manufacturerAddress.setIdentifier("manufacturerAddress");
        manufacturerAddress.setName("Manufacturer Address");
        manufacturerAddress.setPlaceHolder("Enter Manufacturer Address");
        manufacturerAddress.setType("TEXT");
        manufacturerAddress.setRequired(true);
        manufacturerAddress.setExclamationDesc("Please enter the product name only. Avoid adding details like size, weight, price, or dimensions.");
        manufacturerAddress.setMinLength("3");
        manufacturerAddress.setMaxLength("200");
        manufacturerAddress.setPattern("");
        manufacturerAddress.setDescription("Please enter the product name only. Avoid adding details like size, weight, price, or dimensions.");
        manufacturerAddress.setIsFieldDisabled("");

        FormBuilderTool manufacturerPincode = new FormBuilderTool();
        manufacturerPincode.setId("01");
        manufacturerPincode.setIdentifier("manufacturerPincode");
        manufacturerPincode.setName("Manufacturer Pincode");
        manufacturerPincode.setPlaceHolder("Enter Manufacturer Pincode");
        manufacturerPincode.setType("NUMBER");
        manufacturerPincode.setRequired(true);
        manufacturerPincode.setExclamationDesc("Please enter the product name only. Avoid adding details like size, weight, price, or dimensions.");
        manufacturerPincode.setMinLength("6");
        manufacturerPincode.setMaxLength("6");
        manufacturerPincode.setPattern("");
        manufacturerPincode.setDescription("Please enter the product name only. Avoid adding details like size, weight, price, or dimensions.");
        manufacturerPincode.setIsFieldDisabled("");


        List<FormBuilderTool> listOfProductDetails = new ArrayList<>();
        listOfProductDetails.add(color);
        listOfProductDetails.add(netQuantity);
        listOfProductDetails.add(neck);
        listOfProductDetails.add(occasion);
        listOfProductDetails.add(pattern);
        listOfProductDetails.add(sleeveLength);
        listOfProductDetails.add(countryOfOrigin);
        listOfProductDetails.add(manufacturerName);
        listOfProductDetails.add(manufacturerAddress);
        listOfProductDetails.add(manufacturerPincode);
        //PRODUCT DETAILS ENDING




        //ADDITIONAL DETAILS STARTING
        FormBuilderTool brand = new FormBuilderTool();
        brand.setId("01");
        brand.setIdentifier("brand");
        brand.setName("Brand");
        brand.setPlaceHolder("Enter brand");
        brand.setType("TEXT");
        brand.setRequired(true);
        brand.setExclamationDesc("Please enter the Brand name only. Avoid adding details like size, weight, price, or dimensions.");
        brand.setMinLength("");
        brand.setMaxLength("");
        brand.setPattern("");
        brand.setDescription("Please enter the Brand name only. Avoid adding details like size, weight, price, or dimensions.");
        brand.setIsFieldDisabled("");


        FormBuilderTool lining = new FormBuilderTool();
        lining.setId("13");
        lining.setIdentifier("lining");
        lining.setName("Lining");
        lining.setPlaceHolder("Enter lining");
        lining.setType("DROPDOWN");
        lining.setRequired(true);
        lining.setExclamationDesc("Enter the main visible color of your lining (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        lining.setMinLength("");
        lining.setMaxLength("");
        lining.setPattern("");
        lining.setDescription("Enter the lining visible color of your product (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        lining.setIsFieldDisabled("");
        List<String> liningList = new ArrayList<>();
        liningList.add("No Lining");
        liningList.add("Full Lining");
        liningList.add("Half Lining (Upper/Lower)");
        liningList.add("Partial Lining (Selective areas)");
        liningList.add("Attached Lining");
        liningList.add("Detachable Lining");
        liningList.add("Inner Slip Included");
        lining.setValues(liningList);

        FormBuilderTool closureType = new FormBuilderTool();
        closureType.setId("13");
        closureType.setIdentifier("closureType");
        closureType.setName("Closure Type");
        closureType.setPlaceHolder("Enter C losureType");
        closureType.setType("DROPDOWN");
        closureType.setRequired(true);
        closureType.setExclamationDesc("Enter the main visible color of your closureType (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        closureType.setMinLength("");
        closureType.setMaxLength("");
        closureType.setPattern("");
        closureType.setDescription("Enter the lining visible color of your closureType (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        closureType.setIsFieldDisabled("");
        List<String> closureTypeList = new ArrayList<>();
        closureTypeList.add("Zipper");
        closureTypeList.add("Tie-Up");
        closureTypeList.add("Hook & Eye");
        closureTypeList.add("Drawstring");
        closureTypeList.add("Slip-On / Pull-On");
        closureTypeList.add("Velcro");
        closureTypeList.add("Toggle");
        closureType.setValues(closureTypeList);

        FormBuilderTool stretchType = new FormBuilderTool();
        stretchType.setId("13");
        stretchType.setIdentifier("stretchType");
        stretchType.setName("Stretch Type");
        stretchType.setPlaceHolder("Enter StretchType");
        stretchType.setType("DROPDOWN");
        stretchType.setRequired(true);
        stretchType.setExclamationDesc("Enter the StretchType visible color of your closureType (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        stretchType.setMinLength("");
        stretchType.setMaxLength("");
        stretchType.setPattern("");
        stretchType.setDescription("Enter the StretchType visible color of your closureType (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        stretchType.setIsFieldDisabled("");
        List<String> stretchTypeList = new ArrayList<>();
        stretchTypeList.add("No Stretch");
        stretchTypeList.add("Low Stretch");
        stretchTypeList.add("Medium Stretch");
        stretchTypeList.add("High Stretch");
        stretchTypeList.add("Super Stretch");
        stretchType.setValues(stretchTypeList);


        FormBuilderTool careInstruction = new FormBuilderTool();
        careInstruction.setId("13");
        careInstruction.setIdentifier("careInstruction");
        careInstruction.setName("Care Instruction");
        careInstruction.setPlaceHolder("Enter careInstruction");
        careInstruction.setType("DROPDOWN");
        careInstruction.setRequired(true);
        careInstruction.setExclamationDesc("Enter the careInstruction visible color of your closureType (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        careInstruction.setMinLength("");
        careInstruction.setMaxLength("");
        careInstruction.setPattern("");
        careInstruction.setDescription("Enter the careInstruction visible color of your closureType (e.g., Red, Black, Navy Blue). Use simple names to help customers search easily.");
        careInstruction.setIsFieldDisabled("");
        List<String> careInstructionList = new ArrayList<>();
        careInstructionList.add("Machine Wash");
        careInstructionList.add("Hand Wash");
        careInstructionList.add("Dry Clean Only");
        careInstructionList.add("Do Not Bleach");
        careInstructionList.add("Tumble Dry Low");
        careInstructionList.add("Line Dry / Hang Dry");
        careInstructionList.add("Do Not Iron");
        careInstructionList.add("Cool Iron");
        careInstructionList.add("Wash with Like Colors");
        careInstructionList.add("Gentle Wash");
        careInstruction.setValues(careInstructionList);

        FormBuilderTool description = new FormBuilderTool();
        description.setId("01");
        description.setIdentifier("description");
        description.setName("Description");
        description.setPlaceHolder("Enter description");
        description.setType("TEXT");
        description.setRequired(true);
        description.setExclamationDesc("Please enter the description name only. Avoid adding details like size, weight, price, or dimensions.");
        description.setMinLength("");
        description.setMaxLength("");
        description.setPattern("");
        description.setDescription("Please enter the description name only. Avoid adding details like size, weight, price, or dimensions.");
        description.setIsFieldDisabled("");


        FormBuilderTool cottonType = new FormBuilderTool();
        cottonType.setId("01");
        cottonType.setIdentifier("cotton");
        cottonType.setName("cotton");
        cottonType.setPlaceHolder("Enter Cotton");
        cottonType.setType("TEXT");
        cottonType.setRequired(true);
        cottonType.setExclamationDesc("Please enter the Cotton Type name only, or dimensions.");
        cottonType.setMinLength("");
        cottonType.setMaxLength("");
        cottonType.setPattern("");
        cottonType.setDescription("Please enter the cotton name only. Avoid adding details like size, weight, price, or dimensions.");
        cottonType.setIsFieldDisabled("");


        //ADDITIONAL DETAILS ENDING
        List<FormBuilderTool> additionalDetails = new ArrayList<>();
        additionalDetails.add(brand);
        additionalDetails.add(lining);
        additionalDetails.add(closureType);
        additionalDetails.add(stretchType);
        additionalDetails.add(careInstruction);
        additionalDetails.add(description);
        additionalDetails.add(cottonType);


        FormRootCapture formRootCapture = new FormRootCapture();
        formRootCapture.setInventoryData(listOfInventory);
        formRootCapture.setRows(rows);
        formRootCapture.setProductDetails(listOfProductDetails);
        formRootCapture.setAdditionalDetails(additionalDetails);

        Gson gson = new Gson();
        String engineXDummyData = gson.toJson(formRootCapture);
        System.out.println(engineXDummyData);
        System.out.println("======================================");

        return engineXDummyData;
    }
}
