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
import org.acme.dto.RegistrationRequest;
import org.acme.exception.RegistrationFailedException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Collections;
import java.util.HashMap;

@Slf4j
@ApplicationScoped
public class AuthService {

    private final Keycloak keycloak;
    @ConfigProperty(name = "quarkus.keycloak.admin-client.realm")
    private String realm;

    @Inject
    public AuthService(@Named("keycloakClient") Keycloak keycloak) {
        this.keycloak = keycloak;
    }


    public Uni<UserRepresentation> registerUserAccount(RegistrationRequest request) {
        return Uni.createFrom().item(request)
                .onItem().transform(registration -> {
                    log.info("User account creation in-progress for email: {}", registration.getEmail());
                    UserRepresentation userRepresentation = constructUserRepresentation(registration);
                    CredentialRepresentation credentialRepresentation = constructUserCredentials(request.getPassword());
                    userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
                    try (Response response = keycloak.realm(realm).users().create(userRepresentation)) {
                        if(response.getStatus()==Response.Status.CREATED.getStatusCode()) {
                            log.info("User account creation successful, email: {}", registration.getEmail());
                            return userRepresentation;
                        }
                        String errorMessage = (String)response.readEntity(HashMap.class).get(ResponseEntityConstants.ERROR_MESSAGE);
                        log.error("User account creation failed with response code: {}, email: {}, entity: {}",
                                response.getStatus(), registration.getEmail(), errorMessage);
                        throw new RegistrationFailedException(ExceptionMessages.REGISTRATION_FAILED_EXCEPTION
                                + errorMessage,
                                ErrorCodes.REGISTRATION_FAILED);
                    }
                });

    }

    public UserRepresentation constructUserRepresentation(RegistrationRequest request) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(request.getUsername());
        userRepresentation.setEmail(request.getEmail());
        userRepresentation.setFirstName(request.getFirstName());
        userRepresentation.setLastName(request.getLastName());
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }

    public CredentialRepresentation constructUserCredentials(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false);
        return credentialRepresentation;
    }


}
