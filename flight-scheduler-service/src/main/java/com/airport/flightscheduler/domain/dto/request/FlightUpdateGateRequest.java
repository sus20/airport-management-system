package com.airport.flightscheduler.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightUpdateGateRequest {
    @NotBlank(message = "Gate cannot be blank")
    @Pattern(regexp = "^[A-Z]?\\d{1,2}[A-Z]?$",
            message = "Gate format should be like 'A12' or '12B'")
    private String gate;
}