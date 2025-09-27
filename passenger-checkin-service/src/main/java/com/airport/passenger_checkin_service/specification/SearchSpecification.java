package com.airport.passenger_checkin_service.specification;

import com.airport.passenger_checkin_service.domain.dto.request.PassengerSearchRequest;
import com.airport.passenger_checkin_service.specification.criteria.passenger.SearchCriterion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchSpecification {
    private final List<SearchCriterion> criteria;

    public Query toQuery(PassengerSearchRequest request) {
        List<Criteria> parts = criteria.stream()
                .map(criterion -> criterion.toCriteria(request))
                .flatMap(Optional::stream)
                .toList();

        Query query = new Query();
        if (!parts.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(parts.toArray(new Criteria[0])));
        }

        log.info("Criteria query: {}", query);

        return query;
    }
}