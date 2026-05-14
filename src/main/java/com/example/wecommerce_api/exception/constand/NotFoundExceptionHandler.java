package com.example.wecommerce_api.exception.constand;

public class NotFoundExceptionHandler extends RuntimeException{
    public NotFoundExceptionHandler(String message){
        super(message);
    }
}
