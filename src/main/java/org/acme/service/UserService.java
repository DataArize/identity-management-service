package org.acme.service;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.acme.constants.ErrorCodes;
import org.acme.constants.ExceptionMessages;
import org.acme.dto.RegistrationRequest;
import org.acme.dto.RegistrationResponse;
import org.acme.exception.AccountAlreadyExistsException;
import org.acme.model.User;
import org.hibernate.exception.ConstraintViolationException;

@Slf4j
@ApplicationScoped
public class UserService {

    public Uni<RegistrationResponse> persistUser(RegistrationRequest request, String keyCloakUserId) {
        return Panache.withTransaction(() -> PanacheEntityBase.persist(new User(request, keyCloakUserId)))
                .onItem()
                .transform(success -> {
                    log.info("Successfully persisted user, email: {}", request.getEmail());
                    return new RegistrationResponse(request);
                })
                .onFailure(ConstraintViolationException.class)
                .transform(ex -> handleUserCreationError(request.getEmail()));
    }

    private Throwable handleUserCreationError(String email) {
        log.error("User already exists, email: {}", email);
        return new AccountAlreadyExistsException(ExceptionMessages.ACCOUNT_ALREADY_EXISTS + email, ErrorCodes.ACCOUNT_ALREADY_EXISTS);
    }


}
