package com.airport.flightscheduler.search.criteria;

import com.airport.flightscheduler.dto.FlightSearchRequest;
import com.airport.flightscheduler.enumeration.FlightStatus;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StatusCriterion extends BaseCriterion {
    @Override
    public Optional<Criteria> toCriteria(FlightSearchRequest request) {
        FlightStatus status = request.getStatus();
        if(status == null){
            return Optional.empty();
        }
        return matchFieldStrictUppercase("status", status.name());
    }
}