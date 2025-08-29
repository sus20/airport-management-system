package com.airport.flightscheduler.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightEvent {
    private String eventType;
    private Flight flight;
    private Instant timestamp;
}