package com.airport.passenger_checkin_service.search;

import com.airport.passenger_checkin_service.dto.PassengerRequest;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;

public interface SearchCriterion {
    Optional<Criteria> toCriteria( PassengerRequest request );
}