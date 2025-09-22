package com.airport.passenger_checkin_service.service.implementation;

import com.airport.passenger_checkin_service.domain.Passenger;
import com.airport.passenger_checkin_service.dto.PassengerRequest;
import com.airport.passenger_checkin_service.dto.PassengerResponse;
import com.airport.passenger_checkin_service.exception.DuplicatePassengerException;
import com.airport.passenger_checkin_service.mapper.PassengerMapper;
import com.airport.passenger_checkin_service.repository.PassengerRepository;
import com.airport.passenger_checkin_service.service.PassengerService;
import com.airport.passenger_checkin_service.service.validation.PassengerUniquenessRule;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private final PassengerUniquenessRule passportRule;


    @Override
    public PassengerResponse createPassenger(PassengerRequest request) {
        passportRule.validate(request);

        Passenger passenger = passengerMapper.toEntity(request);
        try {
            Passenger savedPassenger = passengerRepository.save(passenger);
            return passengerMapper.toResponse(savedPassenger);
        } catch (DuplicatePassengerException exception) {
            throw new DuplicatePassengerException(
                    String.format("Passenger with passport %s (%s) already exists",
                            request.getPassportNumber(), request.getPassportCountry()));
        }
    }

    @Override
    public PassengerResponse getPassengerById(ObjectId id) {
        return null;
    }

    @Override
    public List<PassengerResponse> getAllPassengers() {
        return passengerRepository.findAll().stream().map(passengerMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public PassengerResponse updatePassenger(ObjectId id, PassengerRequest request) {
        return null;
    }

    @Override
    public void deletePassenger(ObjectId id) {

    }
}