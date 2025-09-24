package com.airport.passenger_checkin_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class FlightCheckInRequest {
    @NotBlank(message = "Flight ID is required")
    private String flightId;
    @NotBlank(message = "Passenger ID is required")
    private String passengerId;

    @NotEmpty(message = "At least one seat number must be provided")
    @Size(max = 2, message = "A passenger cannot reserve more than 2 seats")
    private Set<
            @Pattern(
                    regexp = "^[0-9]{1,2}[A-F]$",
                    message = "Seat must be in format like 12A, 1B, 24F"
            ) String> seatNumbers;

    private boolean baggageChecked;
    @Min(value = 0, message = "Baggage count cannot be negative")
    private int baggageCount;
}