package com.airport.passenger_checkin_service.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventProducer<T> implements EventProducer<T> {
    private final KafkaTemplate<String, T> kafkaTemplate;

    @Override
    public void send(String topic, String key, T event) {
        log.info("Producing event to {}: {}", topic, event);
        kafkaTemplate.send(topic, key, event);
    }
}