package org.acme.exception;

import lombok.Getter;

@Getter
public class NoActiveSessionFoundException extends RuntimeException{
    private final String errorCode;
    public NoActiveSessionFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
