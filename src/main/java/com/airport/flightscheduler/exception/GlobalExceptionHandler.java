package com.airport.flightscheduler.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDetails> handleApiException(
            ApiException ex,
            HttpServletRequest request
    ) {
        ErrorDetails error = new ErrorDetails(
                ex.getStatus().value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        ErrorDetails error = new ErrorDetails(
                500,
                "An unexpected error occurred",
                request.getRequestURI()
        );
        return ResponseEntity.status(500).body(error);
    }
}