package com.airport.passenger_checkin_service.service.implementation;

import com.airport.passenger_checkin_service.domain.event.FlightEvent;
import com.airport.passenger_checkin_service.kafka.handler.FlightEventHandlerRegistry;
import com.airport.passenger_checkin_service.service.FlightSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightSyncServiceImpl implements FlightSyncService {
    private final FlightEventHandlerRegistry handlerRegistry;

    @Override
    public void handleEvent(FlightEvent event) {
        handlerRegistry.getHandler(event.getEventType())
                .ifPresentOrElse(
                        handler -> handler.handle(event.getFlightPayload()),
                        () -> log.warn("Unhandled event type: {}", event.getEventType())
                );
    }
}
