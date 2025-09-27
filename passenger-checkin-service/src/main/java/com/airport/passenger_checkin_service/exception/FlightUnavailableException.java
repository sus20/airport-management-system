package com.airport.passenger_checkin_service.exception;

public class FlightUnavailableException extends RuntimeException{
    public FlightUnavailableException(String message) {
        super(message);
    }
}