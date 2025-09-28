package com.airport.passenger_checkin_service.domain.event;

import com.airport.passenger_checkin_service.domain.entity.FlightReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightEvent {
    private String eventType;
    private FlightReference flightReference;
    private Instant timestamp;
}