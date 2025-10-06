package com.airport.passenger_checkin_service.domain.dto.event;

import com.airport.passenger_checkin_service.domain.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
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