package com.airport.passenger_checkin_service.domain.dto.response;

import com.airport.passenger_checkin_service.domain.enums.CheckInStatus;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
public class FlightCheckInResponse {
    private String id;
    private String flightNumber;
    private Set<String> seatNumbers;
    private Instant checkInTime;
    private String boardingPassUrl;
    private CheckInStatus status;
    private List< BaggageResponse> baggages;
}