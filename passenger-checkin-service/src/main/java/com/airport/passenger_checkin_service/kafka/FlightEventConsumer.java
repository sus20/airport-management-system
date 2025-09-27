package com.airport.passenger_checkin_service.kafka;

import com.airport.passenger_checkin_service.domain.enums.FlightStatus;
import com.airport.passenger_checkin_service.domain.event.FlightEvent;
import com.airport.passenger_checkin_service.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightEventConsumer {
    private final FlightRepository flightRepository;

    @KafkaListener(
            topics = "${app.kafka.topic.flights}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeFlightEvent(FlightEvent event) {
        switch (event.getEventType()) {
            case "CREATED", "UPDATED" -> flightRepository.save(event.getFlight());
            case "DELETED" -> flightRepository.deleteById(event.getFlight().getId());
            case "CANCELLED" -> flightRepository.findById(event.getFlight().getId()).ifPresent(cached -> {
                cached.setStatus(FlightStatus.CANCELLED);
                flightRepository.save(cached);
            });
        }
    }
}