package com.airport.passenger_checkin_service.domain.dto.response;

import com.airport.passenger_checkin_service.domain.enums.BaggageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaggageResponse {
    private String id;
    private String tagNumber;
    private double weight;
    private BaggageStatus status;
}