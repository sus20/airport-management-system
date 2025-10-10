package com.airport.flightscheduler.domain.event;

import com.airport.flightscheduler.domain.enums.FlightEventType;
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
    private FlightEventType eventType;
    private FlightPayload flightPayload;
    private Instant timestamp;
}