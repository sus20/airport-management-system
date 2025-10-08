package com.airport.flightscheduler.kafka.producer;

import com.airport.flightscheduler.domain.enums.UpdateType;
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

    public  void publishFlightCreated(FlightPayload flightPayload){
        FlightEvent event = FlightEvent.builder()
                .eventType("CREATED")
                .flightPayload(flightPayload)
                .timestamp(Instant.now())
                .build();
        eventProducer.send(flightsTopic,flightPayload.getFlightNumber(), event);
    }

    public void publishFlightUpdated(FlightPayload flightPayload, UpdateType updateType) {
        FlightEvent event = FlightEvent.builder()
                .eventType(updateType.name())
                .flightPayload(flightPayload)
                .timestamp(Instant.now())
                .build();

        eventProducer.send(flightsTopic, flightPayload.getFlightNumber(), event);
    }

    public void publishFlightDeleted(FlightPayload flightPayload) {
        FlightEvent event = FlightEvent.builder()
                .eventType("DELETED")
                .flightPayload(flightPayload)
                .timestamp(Instant.now())
                .build();

        eventProducer.send(flightsTopic, flightPayload.getFlightNumber(), event);
    }

}