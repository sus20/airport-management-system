package com.airport.passenger_checkin_service.exception;

public class FlightCheckInNotAllowedException extends RuntimeException {
    public FlightCheckInNotAllowedException(String message) {super(message);}
}