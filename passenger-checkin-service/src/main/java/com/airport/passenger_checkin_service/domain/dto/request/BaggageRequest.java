package com.airport.passenger_checkin_service.domain.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaggageRequest {
    @PositiveOrZero(message = "Weight must be 0 or greater")
    @Max(value = 25, message = "Weight cannot exceed 25 kilograms")
    private double weight;
}