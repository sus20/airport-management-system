package com.airport.passenger_checkin_service.domain.entity;

import com.airport.passenger_checkin_service.domain.enums.BaggageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class BaggageRecord {
    @Id
    private ObjectId id;
    private ObjectId checkInId;
    private String tagNumber;
    private double weight;
    private BaggageStatus status;
}