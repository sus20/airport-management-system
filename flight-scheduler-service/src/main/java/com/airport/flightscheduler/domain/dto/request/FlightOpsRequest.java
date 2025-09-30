package com.airport.flightscheduler.domain.dto.request;

import com.airport.flightscheduler.domain.enums.FlightStatus;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightOpsRequest {
    @Pattern(
            regexp = "^[A-Z]?\\d{1,2}[A-Z]?$",
            message = "Gate format should be like 'A12' or '12B'"
    )
    private String gate;

    @Pattern(regexp = "^[A-Z]$|^[1-9]$", message = "Terminal must be a single letter (A-Z) or a single digit (1-9)")
    private String terminal;

    private FlightStatus flightStatus;
}