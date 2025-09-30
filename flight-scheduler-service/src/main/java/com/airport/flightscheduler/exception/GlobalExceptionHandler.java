package com.airport.flightscheduler.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(
            ApiException ex,
            HttpServletRequest request) {
        return ResponseEntity.status(ex.getStatus()).body(
                buildErrorResponse(ex.getStatus(), ex.getMessage(), request, null)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("exception", ex.getClass().getName());
        errorDetails.put("cause", ex.getCause() != null ? ex.getCause().toString() : "N/A");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request, errorDetails)
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                buildErrorResponse(HttpStatus.NOT_FOUND, "Endpoint not found", request, null)
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("method", ex.getMethod());
        errorDetails.put("supportedMethods", ex.getSupportedHttpMethods() != null
                ? ex.getSupportedHttpMethods().toString()
                : "N/A");

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                buildErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, "HTTP method not supported", request, errorDetails)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                validationErrors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation Failed", request, validationErrors)
        );
    }

    @ExceptionHandler(FlightAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleFlightAlreadyExists(
            FlightAlreadyExistsException ex,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request, null)
        );
    }

    private ErrorResponse buildErrorResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request,
            Map<String, String> errorDetails) {
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .errorDetails(errorDetails != null ? errorDetails : new HashMap<>())
                .build();
    }

}