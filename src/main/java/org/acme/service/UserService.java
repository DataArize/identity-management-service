package org.acme.service;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.acme.dto.RegistrationRequest;
import org.acme.dto.RegistrationResponse;
import org.acme.model.User;
import org.hibernate.exception.ConstraintViolationException;

@Slf4j
@ApplicationScoped
public class UserService {

//    public Uni<RegistrationResponse> persistUser(RegistrationRequest request) {
//        return Panache.withTransaction(() -> PanacheEntityBase.persist(new User(request)))
//                .onItem()
//                .transform(success -> {
//                    log.info("Successfully created user");
//                    return new RegistrationResponse(request.getEmail(), request.getUsername(), request.getDateOfBirth());
//                });
//    }


}
