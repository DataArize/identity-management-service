package org.acme.utils;

import jakarta.ws.rs.core.Response;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SuccessResponseUtils {

    public static Response createSuccessResponse(Object entity, Response.Status status) {
        return Response.status(status).entity(entity).build();
    }
}
