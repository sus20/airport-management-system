package com.airport.passenger_checkin_service.domain.event;

import com.airport.passenger_checkin_service.domain.entity.FlightCheckInRecord;
import com.airport.passenger_checkin_service.domain.enums.CheckInStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckInEvent {
    private CheckInStatus eventType;       // e.g. CHECKED_IN, CANCELED
    private FlightCheckInRecord checkIn;

    @Builder.Default
    private Instant timestamp = Instant.now();
}