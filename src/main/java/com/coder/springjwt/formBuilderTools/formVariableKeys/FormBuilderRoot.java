package com.coder.springjwt.formBuilderTools.formVariableKeys;

import com.coder.springjwt.formBuilderTools.FormBuilderModel.FormBuilderTool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FormBuilderRoot {

    private List<FormBuilderTool> productIdentityList;

    private List<FormBuilderTool> productSizes;

    private List<FormBuilderTool> productVariants;

    private List<FormBuilderTool> productDetails;

    private List<FormBuilderTool> productOtherDetails;

    private List<FormBuilderTool> makerColorAndSize;

    private List<FormBuilderTool> makerAddVariantData;

}
