package com.airport.passenger_checkin_service.kafka.consumer;

import com.airport.passenger_checkin_service.domain.event.FlightEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightEventConsumer {
    private final FlightEventDispatcher dispatcher;

    @KafkaListener(
            topics = "${app.kafka.topic.flights}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeFlightEvent(FlightEvent event) {
        log.info("Dispatcher instance: {}", dispatcher);
        System.out.println("Consuming flight event:" + event);
        log.info("Consuming flight event: {}", event);
        dispatcher.dispatch(event);
    }

}