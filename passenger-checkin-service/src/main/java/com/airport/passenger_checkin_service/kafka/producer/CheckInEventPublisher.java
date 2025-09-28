package com.airport.passenger_checkin_service.kafka.producer;

import com.airport.passenger_checkin_service.domain.event.CheckInEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckInEventPublisher {
    @Value("${app.kafka.topic.passenger-checkins}")
    private String checkinsTopic;

    private final EventProducer<CheckInEvent> producer;

    public void publish(CheckInEvent event) {
        producer.send(checkinsTopic, event.getCheckIn().getId().toHexString(), event);
    }
}