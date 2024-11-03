package org.acme.exception;

import lombok.Getter;

@Getter
public class KeycloakUserCreationFailedException extends RuntimeException{
    private final String errorCode;
    public KeycloakUserCreationFailedException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
