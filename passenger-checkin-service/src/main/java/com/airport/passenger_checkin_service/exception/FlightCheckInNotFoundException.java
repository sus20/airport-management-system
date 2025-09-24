package com.airport.passenger_checkin_service.exception;

public class FlightCheckInNotFoundException extends RuntimeException {
    public FlightCheckInNotFoundException(String message) {
        super(message);
    }
}