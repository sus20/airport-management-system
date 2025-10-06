package com.airport.passenger_checkin_service.kafka.handler;

import com.airport.passenger_checkin_service.domain.enums.UpdateType;
import com.airport.passenger_checkin_service.domain.event.FlightEvent;

public interface FlightEventHandler {
    String getEventType();
    UpdateType getUpdateType();
    void handle(FlightEvent event);
}