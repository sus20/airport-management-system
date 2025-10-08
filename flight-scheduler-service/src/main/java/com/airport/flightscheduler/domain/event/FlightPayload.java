package com.airport.flightscheduler.domain.event;

import com.airport.flightscheduler.domain.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FlightPayload {
    private String id;
    private String flightNumber;

    private String airline;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String aircraftType;
    private FlightStatus status;
    private String gate;
    private String terminal;
    private BigDecimal price;
}
