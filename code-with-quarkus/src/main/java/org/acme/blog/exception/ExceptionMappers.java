package org.acme.blog.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
import org.jboss.logging.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Provider
public class ExceptionMappers {

    private static final Logger LOGGER = Logger.getLogger(ExceptionMappers.class);

    @Provider
    public static class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
        @Override
        public Response toResponse(ConstraintViolationException exception) {
            LOGGER.error("ConstraintViolationException encountered", exception);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", Status.BAD_REQUEST.getStatusCode());
            errorResponse.put("title", "Constraint Violation");

            // Collect all violations
            List<Map<String, String>> violations = exception.getConstraintViolations().stream()
                .map(violation -> {
                    Map<String, String> violationMap = new HashMap<>();
                    violationMap.put("field", violation.getPropertyPath().toString());
                    violationMap.put("message", violation.getMessage());
                    return violationMap;
                })
                .collect(Collectors.toList());

            errorResponse.put("violations", violations);

            return Response.status(Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @Provider
    public static class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
        @Override
        public Response toResponse(WebApplicationException exception) {
            LOGGER.error("WebApplicationException encountered", exception);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", exception.getResponse().getStatus());
            errorResponse.put("error", exception.getMessage());

            return Response.status(exception.getResponse().getStatus())
                           .entity(errorResponse)
                           .build();
        }
    }

    // Optional: Additional mapper for IllegalArgumentException
    @Provider
    public static class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
        @Override
        public Response toResponse(IllegalArgumentException exception) {
            LOGGER.error("IllegalArgumentException encountered", exception);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", Status.BAD_REQUEST.getStatusCode());
            errorResponse.put("error", exception.getMessage());

            return Response.status(Status.BAD_REQUEST)
                           .entity(errorResponse)
                           .build();
        }
    }
}
