package com.coder.springjwt.formBuilderTools.FormBuilderModel;

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

    private List<FormBuilderTools> productIdentityList;

    private List<FormBuilderTools> productSizes;

    private List<FormBuilderTools> productVariants;

    private List<FormBuilderTools> productDetails;

    private List<FormBuilderTools> productOtherDetails;

    private List<FormBuilderTools> makerColorAndSize;

    private List<FormBuilderTools> makerAddVariantData;

}
