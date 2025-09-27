package com.airport.passenger_checkin_service.specification.criteria.passenger;

import com.airport.passenger_checkin_service.domain.dto.request.PassengerSearchRequest;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;

public interface SearchCriterion {
    Optional<Criteria> toCriteria( PassengerSearchRequest request );
}