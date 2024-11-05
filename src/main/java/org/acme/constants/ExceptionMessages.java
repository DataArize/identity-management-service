package org.acme.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ExceptionMessages {

    public static final String INVALID_KEYCLOAK_CLIENT_ID = "Invalid keycloak client id/secret, authentication failed";
    public static final String KEYCLOAK_USER_CREATION_REQUEST_FAILED = "Keycloak user creation request failed";
    public static final String ACCOUNT_ALREADY_EXISTS = "Account already exists for email : ";
    public static final String USER_AUTHENTICATION_FAILED = "User authentication failed. Valid credentials are required to proceed. username: ";
    public static final String NO_ACTIVE_SESSION_FOUND = "No active session found for userId: ";
}
