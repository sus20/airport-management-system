package com.airport.passenger_checkin_service.kafka.handler;

import com.airport.passenger_checkin_service.domain.enums.FlightEventType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FlightEventHandlerRegistry {
    private final List<FlightEventHandler> flightEventHandlers;
    private final Map<FlightEventType, FlightEventHandler> flightEventHandlerMap = new EnumMap<>(FlightEventType.class);

    @PostConstruct
    public void init() {
        for (FlightEventHandler handler : flightEventHandlers) {
            flightEventHandlerMap.put(handler.getSupportedEventType(), handler);
        }
    }

    public Optional<FlightEventHandler> getHandler(FlightEventType type) {
        return Optional.ofNullable(flightEventHandlerMap.get(type));
    }
}
