package com.airport.flightscheduler.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightTerminalUpdateRequest {
    @NotBlank(message = "Terminal cannot be blank")
    @Pattern(regexp = "^[A-Z]$|^[1-9]$", message = "Terminal must be a single letter (A-Z) or a single digit (1-9)")
    private String terminal;
}
