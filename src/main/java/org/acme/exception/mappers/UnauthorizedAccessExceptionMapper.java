package org.acme.exception.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.constants.ExceptionTitle;
import org.acme.exception.UnauthorizedAccessException;
import org.acme.utils.ErrorResponseUtils;

@Provider
public class UnauthorizedAccessExceptionMapper implements ExceptionMapper<UnauthorizedAccessException> {
    @Override
    public Response toResponse(UnauthorizedAccessException ex) {
        return ErrorResponseUtils
                .createErrorResponse(ex.getMessage(),
                        Response.Status.UNAUTHORIZED,
                        ExceptionTitle.AUTHENTICATION_FAILED,
                        ex.getErrorCode());
    }
}
