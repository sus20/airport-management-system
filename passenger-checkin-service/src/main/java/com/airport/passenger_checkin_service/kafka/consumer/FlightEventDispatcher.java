package com.airport.passenger_checkin_service.kafka.consumer;

import com.airport.passenger_checkin_service.domain.event.FlightEvent;
import com.airport.passenger_checkin_service.kafka.handler.FlightEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightEventDispatcher {
    private final List<FlightEventHandler> handlers;

    public void dispatch(FlightEvent event) {
        handlers.stream()
                .filter(h -> h.getEventType().equalsIgnoreCase(event.getEventType()))
                .filter(h -> h.getUpdateType() == null || h.getUpdateType() == event.getUpdateType())
                .findFirst()
                .ifPresentOrElse(
                        h -> h.handle(event),
                        () -> log.warn("No handler for eventType={} updateType={}", event.getEventType(), event.getUpdateType())
                );
    }

}