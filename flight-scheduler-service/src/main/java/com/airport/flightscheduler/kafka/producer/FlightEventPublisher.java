package com.airport.flightscheduler.kafka.producer;

import com.airport.flightscheduler.domain.entity.Flight;
import com.airport.flightscheduler.domain.enums.UpdateType;
import com.airport.flightscheduler.domain.event.FlightEvent;
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

    public  void publishFlightCreated(Flight flight){
        FlightEvent event = FlightEvent.builder()
                .eventType("CREATED")
                .flight(flight)
                .timestamp(Instant.now())
                .build();
        eventProducer.send(flightsTopic,flight.getFlightNumber(), event);
    }

    public void publishFlightUpdated(Flight flight, UpdateType updateType) {
        FlightEvent event = FlightEvent.builder()
                .eventType(updateType.name())
                .flight(flight)
                .timestamp(Instant.now())
                .build();

        eventProducer.send(flightsTopic, flight.getFlightNumber(), event);
    }

    public void publishFlightDeleted(Flight flight) {
        FlightEvent event = FlightEvent.builder()
                .eventType("DELETED")
                .flight(flight)
                .timestamp(Instant.now())
                .build();

        eventProducer.send(flightsTopic, flight.getFlightNumber(), event);
    }

}