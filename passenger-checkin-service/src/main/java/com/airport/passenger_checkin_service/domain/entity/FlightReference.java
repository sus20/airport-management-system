package com.airport.passenger_checkin_service.domain.entity;

import com.airport.passenger_checkin_service.domain.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "flights")
public class FlightReference {
    @Id
    private ObjectId id;
    private String flightNumber;
    private FlightStatus status;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String gate;
    private String terminal;
    private Instant lastUpdated;
}
