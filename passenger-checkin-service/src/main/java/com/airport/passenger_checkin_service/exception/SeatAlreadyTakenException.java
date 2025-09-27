package com.airport.passenger_checkin_service.exception;

import java.util.List;

public class SeatAlreadyTakenException extends RuntimeException {
    public SeatAlreadyTakenException(String flightNumber, List<String> seats) {
        super("Seat(s) " + seats + " already taken for flight " + flightNumber);
    }
}