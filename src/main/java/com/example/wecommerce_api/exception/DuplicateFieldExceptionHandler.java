package com.example.wecommerce_api.exception;

public class DuplicateFieldExceptionHandler extends RuntimeException {
    public DuplicateFieldExceptionHandler(String message){
        super(message);
    }
}
