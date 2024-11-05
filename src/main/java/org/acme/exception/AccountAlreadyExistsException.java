package org.acme.exception;

import lombok.Getter;

@Getter
public class AccountAlreadyExistsException extends RegistrationFailedException{
    private final String errorCode;
    public AccountAlreadyExistsException(String message, String errorCode) {
        super(message, errorCode);
        this.errorCode = errorCode;
    }
}