package com.coder.springjwt.exception.adminException;

public class DataNotFoundException extends  RuntimeException {

    public DataNotFoundException(String message){
        super(message);
    }
}
