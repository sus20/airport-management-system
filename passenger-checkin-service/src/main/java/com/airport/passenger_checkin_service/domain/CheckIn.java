package com.airport.passenger_checkin_service.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckIn {

    @Id
    private ObjectId id;

    @NotNull(message = "Flight ID is required")
    private String flightId;

    @NotNull(message = "Passenger ID is required")
    private String passengerId;

    private String seatNumber;

    private boolean baggageChecked;

    private int baggageCount;

    private LocalDateTime checkInTime;

    private String boardingPassUrl;
}