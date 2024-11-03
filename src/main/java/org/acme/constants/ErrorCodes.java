package org.acme.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ErrorCodes {

    public static final String REGISTRATION_FAILED = "REGISTRATION_FAILED";
    public static final String ACCOUNT_ALREADY_EXISTS = "ACCOUNT_ALREADY_EXISTS";
    public static final String KEYCLOAK_USER_CREATION_FAILED = "KEYCLOAK_USER_CREATION_FAILED";
    public static final String AUTHENTICATION_FAILED = "AUTHENTICATION_FAILED";
}
