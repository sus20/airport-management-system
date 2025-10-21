package com.airport.passenger_checkin_service.exception;

public class SeatAlreadyTakenException extends RuntimeException {
    public SeatAlreadyTakenException(String flightNumber, String seat) {
        super("Seat " + seat + " already taken for flight " + flightNumber);
    }
}