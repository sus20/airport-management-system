package com.airport.passenger_checkin_service.kafka.producer;

import com.airport.passenger_checkin_service.domain.event.CheckInEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckInEventPublisher {
    @Value("${app.kafka.topic.passenger-checkins}")
    private String checkinsTopic;

    private final EventProducer<CheckInEvent> producer;

    public void publish(CheckInEvent event) {
        String key = event.getCheckIn().getId().toHexString();
        producer.send(checkinsTopic, key, event);
        log.info("Published CheckInEvent topic={} key={} passengerId={} flightId={}",
                checkinsTopic,
                key,
                event.getCheckIn().getId(),
                event.getCheckIn().getFlightNumber()
        );
    }
}