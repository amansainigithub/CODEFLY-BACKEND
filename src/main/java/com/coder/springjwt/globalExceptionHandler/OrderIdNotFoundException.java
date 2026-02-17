package com.coder.springjwt.globalExceptionHandler;


public class OrderIdNotFoundException extends RuntimeException {


    public OrderIdNotFoundException(String message){
        super(message);
    }
}
