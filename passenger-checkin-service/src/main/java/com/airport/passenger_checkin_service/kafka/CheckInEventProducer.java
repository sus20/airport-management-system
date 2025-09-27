package com.airport.passenger_checkin_service.kafka;

import com.airport.passenger_checkin_service.domain.event.CheckInEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckInEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topic.passenger-checkins}")
    private String checkinsTopic;

    public void sendCheckInEvent(CheckInEvent event) {
        log.info("Producing check-in event: {}", event);
        kafkaTemplate.send(checkinsTopic, event.getCheckIn().getId().toHexString(), event);
    }
}