package com.airport.flightscheduler.domain.entity;

import com.airport.flightscheduler.domain.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "flights")
@CompoundIndexes({
        @CompoundIndex(name = "unique_flight_schedule", def = "{'flightNumber': 1, 'departureTime': 1}", unique = true)
})
public class Flight {
    @Id
    private ObjectId id;
    private String flightNumber;
    private String airline;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String aircraftType;
    private FlightStatus status;
    private String gate;
    private String terminal;
    private BigDecimal price;
}