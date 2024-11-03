package org.acme.exception;

import lombok.Getter;

@Getter
public class UnauthorizedAccessException extends RuntimeException{
    private final String errorCode;
    public UnauthorizedAccessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
