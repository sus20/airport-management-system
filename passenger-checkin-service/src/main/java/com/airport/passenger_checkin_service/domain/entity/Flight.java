package com.airport.passenger_checkin_service.domain.entity;

import com.airport.passenger_checkin_service.domain.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    @Id
    private ObjectId id;
    private String flightNumber;
    private FlightStatus status;
}
