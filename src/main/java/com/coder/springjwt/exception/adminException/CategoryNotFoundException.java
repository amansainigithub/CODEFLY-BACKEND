package com.coder.springjwt.exception.adminException;

public class CategoryNotFoundException extends  RuntimeException {

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
