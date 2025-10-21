package com.airport.passenger_checkin_service.domain.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class FlightCheckInRequest {
    @NotBlank(message = "Flight Number is required")
    @Pattern(regexp = "^[A-Z0-9]{2,3}[0-9]{1,4}$",
            message = "Flight number must be like EK101 or LH789")
    private String flightNumber;

    @NotBlank(message = "Passport Number is required")
    private String passportNumber;

    @NotEmpty(message = "At least one seat number must be provided")
    @Size(max = 2, message = "A passenger cannot reserve more than 2 seats")
    private Set<
            @Pattern(
                    regexp = "^[0-9]{1,2}[A-F]$",
                    message = "Seat must be in format like 12A, 1B, 24F"
            ) String> seatNumbers;

    private List< @Valid BaggageRequest> baggages;
}