package org.acme.constants;

import lombok.Getter;

@Getter
public enum ResponseType {
    SUCCESS("SUCCESS", "Successful response"),
    FAILED("FAILED", "Failed response");
    private final String value;
    private final String description;

    ResponseType(String value, String description) {
        this.value = value;
        this.description = description;
    }
}
