package com.airport.passenger_checkin_service.kafka.handler;

import com.airport.passenger_checkin_service.domain.enums.UpdateType;
import com.airport.passenger_checkin_service.domain.event.FlightEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightCancelledHandler implements FlightEventHandler {

    @Override
    public String getEventType() {
        return "";
    }

    @Override
    public UpdateType getUpdateType() {
        return null;
    }

    @Override
    public void handle(FlightEvent event) {

    }
}