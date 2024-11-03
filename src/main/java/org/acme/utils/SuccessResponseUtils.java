package org.acme.utils;

import jakarta.ws.rs.core.Response;
import lombok.experimental.UtilityClass;
import org.acme.constants.ResponseType;
import org.acme.dto.SuccessResponse;

@UtilityClass
public class SuccessResponseUtils {

    public static Response createSuccessResponse(Object entity, Response.Status status, String message) {
        SuccessResponse successResponse = new SuccessResponse(ResponseType.SUCCESS.name(), status.getStatusCode(), message, entity);
        return Response.status(status).entity(successResponse).build();
    }
}
