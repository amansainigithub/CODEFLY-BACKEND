package com.coder.springjwt.exception.sellerException;

public class UnauthorizedAndRoleNotFoundException extends RuntimeException {

    public UnauthorizedAndRoleNotFoundException(String msg){
        super(msg);
    }
}
