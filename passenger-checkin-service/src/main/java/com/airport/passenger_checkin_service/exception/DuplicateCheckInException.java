package com.airport.passenger_checkin_service.exception;

public class DuplicateCheckInException extends RuntimeException {
    public DuplicateCheckInException(String flightNumber, String passportNumber) {
        super("Passenger with passport number" + passportNumber + " is already checked in for flight " + flightNumber);
    }
}