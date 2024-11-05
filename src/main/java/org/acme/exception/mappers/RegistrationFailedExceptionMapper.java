package org.acme.exception.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.constants.ExceptionTitle;
import org.acme.exception.RegistrationFailedException;
import org.acme.utils.ErrorResponseUtils;

@Provider
public class RegistrationFailedExceptionMapper implements ExceptionMapper<RegistrationFailedException> {
    @Override
    public Response toResponse(RegistrationFailedException ex) {
        return ErrorResponseUtils
                .createErrorResponse(ex.getMessage(),
                        Response.Status.BAD_REQUEST,
                        ExceptionTitle.REGISTRATION_FAILED,
                        ex.getErrorCode());
    }
}
