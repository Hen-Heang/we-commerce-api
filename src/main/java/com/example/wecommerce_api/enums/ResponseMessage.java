package com.example.wecommerce_api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessage implements IResponseMessage {
    OK("200", "OK"),
    INCORRECT_USERNAME("001", "Incorrect email!"),
    INCORRECT_PASSWORD("002", "Incorrect password!"),
    FORBIDDEN("403", "Forbidden"),
    UNAUTHORIZED("401", "UNAUTHORIZED"),
    REQUESTER("004", "REQUESTER"),
    INVALID_TOKEN_SIGNATURE("0004", "Invalid token signature"),
    INVALID_TOKEN("005", "Invalid token"),
    TOKEN_EXPIRED("006", "Token expired"),
    UNSUPPORTED_TOKEN("007", "Unsupported token"),
    AUTHORIZED("003","NOT YET AUTHORIZED!"),
    CHECKPOINTING("009","PHONE NUMBER HAVE ALREADY"),

    ;
    private final String code;
    private final String message;
}
