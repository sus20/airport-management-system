package com.airport.passenger_checkin_service.domain.dto.request;

import com.airport.passenger_checkin_service.domain.enums.BaggageStatus;
import lombok.Data;

@Data
public class BaggageStatusUpdateRequest {
    BaggageStatus status;
}