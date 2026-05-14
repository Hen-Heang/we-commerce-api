package com.example.wecommerce_api.exception.constand;

public class PaymentRequired extends RuntimeException{
    public PaymentRequired(String message){
      super(message);
    }
}
