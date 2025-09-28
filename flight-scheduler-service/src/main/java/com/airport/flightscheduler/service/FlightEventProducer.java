package com.airport.flightscheduler.service;

import com.airport.flightscheduler.domain.event.FlightEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topic.flights}")
    private String flightTopic;

    public void sendFlightEvent(FlightEvent event) {
        log.info("Sending event {} for flight {}", event.getEventType(), event.getFlight().getFlightNumber());
        kafkaTemplate.send(flightTopic, event.getFlight().getId().toString(), event);
    }

}
