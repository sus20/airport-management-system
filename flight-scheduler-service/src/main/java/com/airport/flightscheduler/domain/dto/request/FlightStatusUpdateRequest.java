package com.airport.flightscheduler.domain.dto.request;

import com.airport.flightscheduler.domain.enums.FlightStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightStatusUpdateRequest {
    @NotNull(message = "Status cannot be null")
    private FlightStatus status;
}