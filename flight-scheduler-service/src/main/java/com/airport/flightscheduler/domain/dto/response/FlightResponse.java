package com.airport.flightscheduler.domain.dto.response;

import com.airport.flightscheduler.domain.enums.FlightStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlightResponse {
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