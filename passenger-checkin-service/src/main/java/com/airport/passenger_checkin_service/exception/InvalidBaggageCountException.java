package com.airport.passenger_checkin_service.exception;

public class InvalidBaggageCountException  extends RuntimeException{
    public InvalidBaggageCountException(String message) {
        super(message);
    }
}