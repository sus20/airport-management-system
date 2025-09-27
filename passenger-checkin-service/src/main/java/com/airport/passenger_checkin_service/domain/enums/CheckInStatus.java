package com.airport.passenger_checkin_service.domain.enums;

public enum CheckInStatus {
    BOOKED,       // Reservation confirmed
    CHECKEDIN,    // Checked in, boarding pass issued
    BOARDED,      // Passenger is onboard
    FLOWN,        // Passenger has completed flight
    NOSHOW,       // Passenger failed to show up
    OFFLOADED     // Passenger was checked in but removed
}