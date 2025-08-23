package com.airport.flightscheduler.exception;

import org.springframework.http.HttpStatus;

public class FlightNotFoundException extends ApiException {
    public FlightNotFoundException(Object flightId) {
        super("Flight with id " + flightId + " not found", HttpStatus.NOT_FOUND);
    }
}
