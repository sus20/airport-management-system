package com.airport.passenger_checkin_service.domain.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateSeatRequest {
    @NotEmpty(message = "At least one seat number must be provided")
    @Size(max = 2, message = "A passenger cannot reserve more than 2 seats")
    private Set<
                @Pattern(
                        regexp = "^[0-9]{1,2}[A-F]$",
                        message = "Seat must be in format like 12A, 1B, 24F"
                ) String> seatNumbers;
}