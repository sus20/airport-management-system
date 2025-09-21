package com.airport.flightscheduler.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDetails> handleApiException(
            ApiException ex,
            HttpServletRequest request
    ) {
        ErrorDetails error = new ErrorDetails(
                ex.getStatus().value(),
                ex.getClass().getSimpleName(),
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
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getClass().getSimpleName(),
                "An unexpected error occurred",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorDetails> handleNoResourceFound(
            NoResourceFoundException ex,
            HttpServletRequest request
    ) {
        ErrorDetails error = new ErrorDetails(
                HttpStatus.NOT_FOUND.value(),
                ex.getClass().getSimpleName(),
                "Endpoint not found",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDetails> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request
    ) {
        ErrorDetails error = new ErrorDetails(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                ex.getClass().getSimpleName(),
                "HTTP method not supported",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        ErrorDetails error = new ErrorDetails(
                HttpStatus.BAD_REQUEST.value(),
                ex.getClass().getSimpleName(),
                "Validation failed",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}