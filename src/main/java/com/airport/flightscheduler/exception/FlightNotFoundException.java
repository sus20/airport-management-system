package com.airport.flightscheduler.exception;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(Object flightId) {
        super("Flight with id " + flightId + " not found");
    }
}
