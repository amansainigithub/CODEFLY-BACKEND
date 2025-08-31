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
public class FormBuilderTool {

    private String id;
    private String identifier;
    private String name;
    private String type;
    private boolean required;
    private String placeHolder;
    private String exclamationDesc;
    private String minLength;
    private String maxLength;
    private String min;
    private String max;
    private String pattern;
    private String description;
    private String isFieldDisabled;
    private List<String> values;
}
