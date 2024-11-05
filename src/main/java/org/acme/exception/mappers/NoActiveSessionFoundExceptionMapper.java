package org.acme.exception.mappers;

import io.quarkus.runtime.util.ExceptionUtil;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.constants.ErrorCodes;
import org.acme.constants.ExceptionTitle;
import org.acme.exception.NoActiveSessionFoundException;
import org.acme.utils.ErrorResponseUtils;

@Provider
public class NoActiveSessionFoundExceptionMapper implements ExceptionMapper<NoActiveSessionFoundException> {
    @Override
    public Response toResponse(NoActiveSessionFoundException ex) {
                return ErrorResponseUtils
                        .createErrorResponse(ex.getMessage(),
                                Response.Status.UNAUTHORIZED,
                                ExceptionTitle.NO_ACTIVE_SESSION_FOUND,
                                ex.getErrorCode());
    }
}
