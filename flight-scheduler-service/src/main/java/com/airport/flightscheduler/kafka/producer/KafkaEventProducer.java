package com.airport.flightscheduler.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventProducer<T> implements EventProducer<T> {
    private final KafkaTemplate<String, T> kafkaTemplate;

    @Override
    public void send(String topic, String key, T value) {
        kafkaTemplate.send(topic, key, value);
    }
}