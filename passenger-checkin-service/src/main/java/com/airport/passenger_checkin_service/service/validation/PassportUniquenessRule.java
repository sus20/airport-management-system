package com.airport.passenger_checkin_service.service.validation;


import com.airport.passenger_checkin_service.dto.PassengerRequest;
import com.airport.passenger_checkin_service.exception.DuplicatePassengerException;
import com.airport.passenger_checkin_service.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PassportUniquenessRule implements PassengerUniquenessRule {
    private final PassengerRepository passengerRepository;

    @Override
    public void validate(PassengerRequest request) {
        passengerRepository.findByPassportNumberAndPassportCountry(
                request.getPassportNumber(), request.getPassportCountry()
        ).ifPresent(passenger -> {
            throw new DuplicatePassengerException(String.format("Passenger with passport %s (%s) already exists",
                    request.getPassportNumber(), request.getPassportCountry()));
        });
    }
}