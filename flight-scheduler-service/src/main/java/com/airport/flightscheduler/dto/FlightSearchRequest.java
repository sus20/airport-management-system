package com.airport.flightscheduler.dto;

import com.airport.flightscheduler.enumeration.FlightStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FlightSearchRequest {
    private String flightNumber;
    private String airline;
    private String origin;
    private String destination;
    private FlightStatus status;
    private String aircraftType;
    private String gate;
    private String terminal;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime departureFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime departureTo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime arrivalFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime arrivalTo;

    private BigDecimal priceMin;
    private BigDecimal priceMax;
}