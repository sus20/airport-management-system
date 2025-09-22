package com.airport.passenger_checkin_service.dto;

import com.airport.passenger_checkin_service.domain.enums.Gender;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class PassengerResponse {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private String address;
    private String passportNumber;
    private String passportCountry;
    private LocalDate passportExpiryDate;
    private String nationality;
    private Instant createdAt;
    private Instant updatedAt;
}