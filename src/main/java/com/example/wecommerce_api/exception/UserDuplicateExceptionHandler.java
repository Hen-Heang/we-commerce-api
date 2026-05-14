package com.example.wecommerce_api.exception;

public class UserDuplicateExceptionHandler extends RuntimeException{
    public UserDuplicateExceptionHandler(String message){
        super(message);
    }
}
