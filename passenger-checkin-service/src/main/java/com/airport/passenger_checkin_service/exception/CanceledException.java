package com.airport.passenger_checkin_service.exception;

public class CanceledException extends RuntimeException {
    public CanceledException(String message) {
        super(message);
    }
}