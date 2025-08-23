package com.airport.flightscheduler.service.search;

import com.airport.flightscheduler.domain.FlightSearchRequest;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;

public interface FlightSearchCriterion {
    Optional<Criteria> toCriteria(FlightSearchRequest request);
}