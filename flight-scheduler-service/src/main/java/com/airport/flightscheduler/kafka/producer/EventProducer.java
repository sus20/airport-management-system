package com.airport.flightscheduler.kafka.producer;

public interface EventProducer<T> {
    void send(String topic, String key, T value);
}