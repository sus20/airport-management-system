package com.airport.passenger_checkin_service.domain.enums;

public enum BaggageStatus {
    CHECKED_IN,     // passenger handed over
    SCREENED,    // security check
    LOADED,      // on aircraft
    IN_TRANSIT,  // between airports
    ARRIVED,     // at destination airport
    DELIVERED,   // at belt / collected
    OFFLOADED,   // removed (e.g., passenger is NO_SHOW)
    LOST         // missing
}