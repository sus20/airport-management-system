package com.airport.passenger_checkin_service.exception;

public class DuplicateCheckInException extends RuntimeException {
    public DuplicateCheckInException(String flightId, String passengerId) {
        super("Passenger " + passengerId + " is already checked in for flight " + flightId);
    }
}