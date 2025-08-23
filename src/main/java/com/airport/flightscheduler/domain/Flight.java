package com.airport.flightscheduler.domain;

import com.airport.flightscheduler.enumeration.FlightStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_DEFAULT)
@Document(collection = "flights")
public class Flight {

    @Id
    private ObjectId id;

    @NotBlank(message = "Flight number is required")
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

    @NotNull(message = "Status is required")
    private FlightStatus status;

    @NotBlank(message = "Aircraft type is required")
    private String aircraftType;

    private String gate;
    private String terminal;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private BigDecimal price;
}
