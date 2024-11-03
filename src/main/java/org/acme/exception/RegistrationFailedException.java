package org.acme.exception;

import lombok.Getter;

@Getter
public class RegistrationFailedException extends RuntimeException{
    private final String errorCode;
    public RegistrationFailedException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
