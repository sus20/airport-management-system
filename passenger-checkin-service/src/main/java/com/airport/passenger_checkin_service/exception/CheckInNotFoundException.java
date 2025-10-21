package com.airport.passenger_checkin_service.exception;

public class CheckInNotFoundException extends RuntimeException {
    public CheckInNotFoundException(String message) {
        super(message);
    }
}