package com.coder.springjwt.controllers.seller.formBuilderController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FormRootCapture {


    private List<FormBuilderTool>  inventoryData;
    private List<FormBuilderTool>  rows;
    private List<FormBuilderTool>  productDetails;
}
