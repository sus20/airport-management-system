package com.airport.flightscheduler.kafka.producer;

import com.airport.flightscheduler.domain.enums.FlightEventType;
import com.airport.flightscheduler.domain.event.FlightEvent;
import com.airport.flightscheduler.domain.event.FlightPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class FlightEventPublisher {
    @Value("${app.kafka.topic.flights}")
    private  String flightsTopic;

    private final EventProducer<FlightEvent> eventProducer;

    public void publish(FlightPayload flightPayload, FlightEventType eventType) {
        FlightEvent event = FlightEvent.builder()
                .eventType(eventType)
                .flightPayload(flightPayload)
                .timestamp(Instant.now())
                .build();
        eventProducer.send(flightsTopic, flightPayload.getFlightNumber(), event);
    }
}