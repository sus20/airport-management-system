package com.airport.passenger_checkin_service.domain.entity;

import com.airport.passenger_checkin_service.domain.enums.CheckInStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Document(collection = "flight-checkins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndexes({
        @CompoundIndex(
                name = "unique_flight_passenger_idx",
                def = "{'flightId': 1, 'passportNumber': 1}",
                unique = true
        )
})
public class FlightCheckInRecord {

    @Id
    private ObjectId id;
    private String flightNumber;
    private String passportNumber;
    private Set<String> seatNumbers;

    @CreatedDate
    private Instant checkInTime;
    private String boardingPassUrl;
    private CheckInStatus status;
    private List<ObjectId> baggageIds;
}