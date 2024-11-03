package org.acme.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ExceptionMessages {

    public static final String REGISTRATION_FAILED_EXCEPTION = "User account creation failed, message: ";
    public static final String KEYCLOAK_USER_CREATION_REQUEST_FAILED = "Keycloak user creation request failed";
    public static final String ACCOUNT_ALREADY_EXISTS = "Account already exists for email : ";
    public static final String USER_AUTHENTICATION_FAILED = "User authentication failed. Valid credentials are required to proceed. username: ";
}
