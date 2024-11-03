package org.acme.resource;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.acme.constants.ErrorCodes;
import org.acme.constants.ExceptionTitle;
import org.acme.dto.RegistrationRequest;
import org.acme.dto.RegistrationResponse;
import org.acme.exception.RegistrationFailedException;
import org.acme.service.AuthService;
import org.acme.utils.ErrorResponseUtils;
import org.acme.utils.SuccessResponseUtils;

@Slf4j
@Path("/auth")
@ApplicationScoped
public class AuthResource {

    private final AuthService authService;

    @Inject
    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path("/register")
    public Uni<Response> registerNewUser(RegistrationRequest request) {
        return authService.registerUserAccount(request).onItem().transform(success -> {
            RegistrationResponse response = new RegistrationResponse(
                    success.getEmail(),
                    request.getUsername(),
                    request.getFirstName(),
                    request.getLastName());
            return SuccessResponseUtils.createSuccessResponse(response, Response.Status.CREATED);
        }).onFailure(RegistrationFailedException.class)
                .recoverWithItem(ex -> ErrorResponseUtils
                        .createErrorResponse(ex.getMessage(),
                                Response.Status.BAD_REQUEST,
                                ExceptionTitle.REGISTRATION_FAILED,
                                ErrorCodes.REGISTRATION_FAILED))
        .onFailure()
                .recoverWithItem(ex -> ErrorResponseUtils
                .createErrorResponse(ex.getMessage(),
                Response.Status.INTERNAL_SERVER_ERROR,
                ExceptionTitle.REGISTRATION_FAILED,
                ErrorCodes.REGISTRATION_FAILED));
    }

}
