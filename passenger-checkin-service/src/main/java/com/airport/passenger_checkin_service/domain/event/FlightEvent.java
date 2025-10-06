package com.airport.passenger_checkin_service.domain.event;

import com.airport.passenger_checkin_service.domain.dto.event.FlightPayload;
import com.airport.passenger_checkin_service.domain.enums.UpdateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightEvent {
    private String eventType;
    private FlightPayload flightPayload;
    private UpdateType updateType;
}