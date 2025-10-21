package com.airport.passenger_checkin_service.repository;

import com.airport.passenger_checkin_service.domain.entity.BaggageRecord;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BaggageRepository extends MongoRepository<BaggageRecord, ObjectId> {
    List<BaggageRecord> findByCheckInId(ObjectId checkInId);
}