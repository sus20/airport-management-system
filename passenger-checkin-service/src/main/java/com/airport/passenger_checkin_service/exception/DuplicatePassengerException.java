package com.airport.passenger_checkin_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicatePassengerException  extends RuntimeException{
    public DuplicatePassengerException( String message ) {
        super( message);
    }
}