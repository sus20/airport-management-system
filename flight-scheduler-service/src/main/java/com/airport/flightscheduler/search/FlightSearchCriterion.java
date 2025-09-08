package com.airport.flightscheduler.search;

import com.airport.flightscheduler.dto.FlightSearchRequest;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;

public interface FlightSearchCriterion {
    Optional<Criteria> toCriteria(FlightSearchRequest request);
}