package com.airport.passenger_checkin_service.domain.dto.response;

import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class FlightCheckInResponse {
    private String id;
    private String flightNumber;
    private Set<String> seatNumbers;
    private boolean baggageChecked;
    private int baggageCount;
    private String boardingPassUrl;
    private Instant checkInTime;
    private Instant updatedAt;
}