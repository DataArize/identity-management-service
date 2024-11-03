package org.acme.exception;

import lombok.Getter;

@Getter
public class AccountAlreadyExistsException extends RuntimeException{
    private final String errorCode;
    public AccountAlreadyExistsException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}