package com.airport.passenger_checkin_service.exception;

public class MaxSeatLimitExceededException extends RuntimeException{
    public MaxSeatLimitExceededException(String message) {
        super(message);
    }
}