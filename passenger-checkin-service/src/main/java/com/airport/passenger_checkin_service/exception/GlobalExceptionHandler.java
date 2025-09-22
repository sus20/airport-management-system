package com.airport.passenger_checkin_service.exception;

import com.airport.passenger_checkin_service.dto.ErrorDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatePassengerException.class)
    public ResponseEntity<ErrorDetails> handleDuplicatePassenger(
            DuplicatePassengerException ex,
            HttpServletRequest request) {
        ErrorDetails error = ErrorDetails.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }
}