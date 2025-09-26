package com.airport.passenger_checkin_service.search;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;
import java.util.regex.Pattern;

public abstract class BaseCriterion implements SearchCriterion {
    protected Optional<Criteria> matchFieldWithCaseInsensitiveRegex(String field, String value) {
        if (value == null || value.isBlank()) return Optional.empty();
        return Optional.of(
                Criteria.where(field).regex(Pattern.compile("^" + value.trim(), Pattern.CASE_INSENSITIVE))
        );
    }

    protected <E extends Enum<E>> Optional<Criteria> matchFieldWithEnum(String field, E value) {
        if (value == null) return Optional.empty();
        return Optional.of(Criteria.where(field).is(value));
    }
}
