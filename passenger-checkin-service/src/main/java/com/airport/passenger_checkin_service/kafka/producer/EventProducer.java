package com.airport.passenger_checkin_service.kafka.producer;

public interface EventProducer<T> {
    void send(String topic, String key, T event);
}