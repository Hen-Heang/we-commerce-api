package com.example.wecommerce_api.exception;

public class FieldEmptyExceptionHandler extends RuntimeException{
    public FieldEmptyExceptionHandler(String message){
        super(message);
    }
}
