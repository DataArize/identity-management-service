package org.acme.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.acme.dto.LoginRequest;
import org.acme.dto.LoginResponse;
import org.acme.dto.RegistrationRequest;
import org.acme.dto.RegistrationResponse;

@Slf4j
@ApplicationScoped
public class AuthService {

    private final KeycloakService keycloakService;
    private final UserService userService;

    @Inject
    public AuthService(KeycloakService keycloakService, UserService userService) {
        this.keycloakService = keycloakService;
        this.userService = userService;
    }


    public Uni<RegistrationResponse> registerUserAccount(RegistrationRequest request) {
        return keycloakService.createKeycloakUser(request).onItem()
                .transformToUni(userRepresentation -> userService.persistUser(request, userRepresentation.getId()).onItem()
                            .transform(success -> success));
    }

    public Uni<LoginResponse> authenticateUser(LoginRequest request) {
        return keycloakService.generateAccessToken(request)
                .onItem()
                .transform(LoginResponse::new);

    }

    public Uni<Void> invalidateUserSession(String userId) {
        return keycloakService.deleteUserSessions(userId).replaceWithVoid();

    }




}
