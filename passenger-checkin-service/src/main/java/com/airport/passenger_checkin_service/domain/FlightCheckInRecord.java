package com.airport.passenger_checkin_service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Document(collection = "flight-checkins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndexes({
        @CompoundIndex(
                name = "unique_flight_passenger_idx",
                def = "{'flightId': 1, 'passengerId': 1}",
                unique = true
        )
})
public class FlightCheckInRecord {

    @Id
    private ObjectId id;

    private String flightId;
    private String passengerId;
    private Set<String> seatNumbers;
    private boolean baggageChecked;
    private int baggageCount;

    @CreatedDate
    private Instant checkInTime;
    private String boardingPassUrl;

    @LastModifiedDate
    private Instant updatedAt;
}