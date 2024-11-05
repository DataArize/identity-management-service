package org.acme.exception;

import lombok.Getter;

@Getter
public class KeycloakUserCreationFailedException extends RegistrationFailedException{
    private final String errorCode;
    public KeycloakUserCreationFailedException(String message, String errorCode) {
        super(message, errorCode);
        this.errorCode = errorCode;
    }
}
