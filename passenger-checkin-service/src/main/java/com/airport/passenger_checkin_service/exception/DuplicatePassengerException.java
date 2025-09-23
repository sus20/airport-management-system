package com.airport.passenger_checkin_service.exception;

public class DuplicatePassengerException  extends RuntimeException{
    public DuplicatePassengerException( String message ) {
        super( message);
    }
}