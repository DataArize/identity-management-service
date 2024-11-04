package org.acme.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.acme.constants.ErrorCodes;
import org.acme.constants.ExceptionMessages;
import org.acme.constants.ResponseEntityConstants;
import org.acme.dto.LoginRequest;
import org.acme.dto.RegistrationRequest;
import org.acme.exception.KeycloakUserCreationFailedException;
import org.acme.exception.NoActiveSessionFoundException;
import org.acme.exception.UnauthorizedAccessException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.UserSessionRepresentation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@ApplicationScoped
public class KeycloakService {

    private final Keycloak keycloak;
    private final KeycloakBuilder keycloakBuilder;
    @ConfigProperty(name = "quarkus.keycloak.admin-client.realm")
    private String realm;

    @Inject
    public KeycloakService(@Named("keycloakClient") Keycloak keycloak, @Named("keycloakClientBuilder") KeycloakBuilder keycloakBuilder) {
        this.keycloak = keycloak;
        this.keycloakBuilder = keycloakBuilder;
    }

    public Uni<UserRepresentation> createKeycloakUser(RegistrationRequest request) {
        return Uni.createFrom().item(request)
                .onItem()
                .transform(registration -> {
                    log.info("Keycloak user account creation in-progress for email: {}", registration.getEmail());
                    UserRepresentation userRepresentation = constructUserRepresentation(registration);
                    CredentialRepresentation credentialRepresentation = constructUserCredentials(request.getPassword());
                    userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
                    try (Response response = keycloak.realm(realm).users().create(userRepresentation)) {
                        if(response.getStatus()==Response.Status.CREATED.getStatusCode()) {
                            log.info("Keycloak user account creation successful, email: {}", registration.getEmail());
                            return userRepresentation;
                        }
                        String errorMessage = (String)response.readEntity(HashMap.class).get(ResponseEntityConstants.ERROR_MESSAGE);
                        log.error("Keycloak user account creation failed with response code: {}, email: {}, entity: {}",
                                response.getStatus(), registration.getEmail(), errorMessage);
                        throw new KeycloakUserCreationFailedException(ExceptionMessages.KEYCLOAK_USER_CREATION_REQUEST_FAILED
                                + errorMessage,
                                ErrorCodes.KEYCLOAK_USER_CREATION_FAILED);
                    }
                });
    }

    public UserRepresentation constructUserRepresentation(RegistrationRequest request) {
        log.info("Constructing user representation object for email: {}", request.getEmail());
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(request.getUsername());
        userRepresentation.setEmail(request.getEmail());
        userRepresentation.setFirstName(request.getFirstName());
        userRepresentation.setLastName(request.getLastName());
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }

    public CredentialRepresentation constructUserCredentials(String password) {
        log.info("Constructing credential representation object");
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false);
        return credentialRepresentation;
    }

    public Uni<AccessTokenResponse> generateAccessToken(LoginRequest request) {
        return Uni.createFrom().item(request)
                .onItem()
                .transformToUni(loginRequest -> {
                    log.info("Attempting to authenticate user: {}", request.getUsername());
                    try (Keycloak keycloakClient = keycloakBuilder
                            .username(request.getUsername())
                            .password(request.getPassword()).build()) {
                        AccessTokenResponse grantToken = keycloakClient.tokenManager().grantToken();
                        log.info("User authentication successful, username: {}", request.getUsername());
                        return Uni.createFrom().item(grantToken);
                    }
                    catch (Exception ex) {
                        log.error("User authentication failed, username: {}", request.getUsername());
                        throw new UnauthorizedAccessException(ExceptionMessages.USER_AUTHENTICATION_FAILED + request.getUsername(), ErrorCodes.AUTHENTICATION_FAILED);
                    }
                });
    }

    public Uni<Void> deleteUserSessions(String userId) {
        return Uni.createFrom().item(userId)
                .onItem()
                .transformToUni(user -> {
                    log.info("Attempting to invalidate session for user: {}", userId);
                    RealmResource realmResource = keycloak.realm(realm);
                    List<UserSessionRepresentation> userSessions =
                            realmResource.users().get(userId).getUserSessions();
                    if(userSessions.isEmpty()) {
                        log.error("No active sessions found for the user, userId: {}", userId);
                        throw new NoActiveSessionFoundException(ExceptionMessages.NO_ACTIVE_SESSION_FOUND + userId, ErrorCodes.NO_ACTIVE_SESSION);
                    }
                    for (UserSessionRepresentation sessionRepresentation: userSessions) {
                        realmResource.deleteSession(sessionRepresentation.getId(), true);
                    }
                    log.info("User session invalidated successfully, user id: {}", userId);
                    return Uni.createFrom().voidItem();
                }).replaceWithVoid();
    }
}
