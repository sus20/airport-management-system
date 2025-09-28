package com.airport.passenger_checkin_service.domain.entity;

import com.airport.passenger_checkin_service.domain.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "flights")
public class Flight {
    @Id
    private ObjectId id;

    private String flightNumber;
    private FlightStatus status;
}
