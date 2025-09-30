package com.airport.flightscheduler.domain.dto.request;

import com.airport.flightscheduler.domain.dto.request.validation.ValidFlightRequest;
import com.airport.flightscheduler.domain.enums.FlightStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@ValidFlightRequest
public class FlightRequest {
    @NotBlank(message = "Flight number is required")
    @Pattern(regexp = "^[A-Z0-9]{2,3}[0-9]{1,4}$",
            message = "Flight number must be like EK101 or LH789")
    private String flightNumber;

    @NotBlank(message = "Airline name is required")
    private String airline;

    @NotBlank(message = "Origin airport code is required")
    @Size(min = 3, max = 3, message = "Origin must be a 3-letter IATA code")
    private String origin;

    @NotBlank(message = "Destination airport code is required")
    @Size(min = 3, max = 3, message = "Destination must be a 3-letter IATA code")
    private String destination;

    @NotNull(message = "Departure  time is required")
    @Future(message = "Departure time must be in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    @Future(message = "Arrival time must be in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime arrivalTime;

    @NotBlank(message = "Aircraft type is required")
    private String aircraftType;

    private FlightStatus status;

    @Pattern(regexp = "^[A-Z]?\\d{1,2}[A-Z]?$",
            message = "Gate format should be like 'A12' or '12B'")
    private String gate;

    @Pattern(regexp = "^[A-Z]$|^[1-9]$", message = "Terminal must be a single letter (A-Z) or a single digit (1-9)")
    private String terminal;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private BigDecimal price;

}
