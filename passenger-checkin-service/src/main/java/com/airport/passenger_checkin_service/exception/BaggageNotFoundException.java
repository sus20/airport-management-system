package com.airport.passenger_checkin_service.exception;

public class BaggageNotFoundException extends RuntimeException{
    public BaggageNotFoundException(String message) {
        super(message);
    }
}