package org.acme.blog.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Provider
public class ExceptionMappers {

    @Provider
    public static class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
        @Override
        public Response toResponse(ConstraintViolationException exception) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 400);
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
            return Response.status(exception.getResponse().getStatus())
                .entity(exception.getMessage())
                .build();
        }
    }
}
