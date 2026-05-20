package com.example.wecommerce_api.exception;

public class InvalidValueExceptionHandler extends RuntimeException{
    public InvalidValueExceptionHandler(String message){
        super(message);
    }
}
