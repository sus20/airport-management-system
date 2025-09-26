package com.airport.passenger_checkin_service.dto;

import com.airport.passenger_checkin_service.domain.enums.Gender;
import lombok.Data;

@Data
public class PassengerSearchRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private Gender gender;
    private String address;
    private String email;
    private String phoneNumber;
    private String passportNumber;
    private String nationality;
}