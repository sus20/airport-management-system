package com.airport.passenger_checkin_service.dto;

import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class FlightCheckInResponse {
    private String id;
    private String flightId;
    private String passengerId;
    private Set<String> seatNumbers;
    private boolean baggageChecked;
    private int baggageCount;
    private Instant checkInTime;
    private String boardingPassUrl;
    private Instant updatedAt;
}