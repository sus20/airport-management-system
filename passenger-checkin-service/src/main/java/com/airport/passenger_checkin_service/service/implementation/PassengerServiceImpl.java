package com.airport.passenger_checkin_service.service.implementation;

import com.airport.passenger_checkin_service.domain.Passenger;
import com.airport.passenger_checkin_service.dto.PassengerRequest;
import com.airport.passenger_checkin_service.dto.PassengerResponse;
import com.airport.passenger_checkin_service.dto.PassengerSearchRequest;
import com.airport.passenger_checkin_service.exception.DuplicatePassengerException;
import com.airport.passenger_checkin_service.exception.PassengerNotFoundException;
import com.airport.passenger_checkin_service.mapper.PassengerMapper;
import com.airport.passenger_checkin_service.repository.PassengerRepository;
import com.airport.passenger_checkin_service.search.SearchSpecification;
import com.airport.passenger_checkin_service.service.PassengerService;
import com.airport.passenger_checkin_service.service.validation.PassengerUniquenessRule;
import com.mongodb.DuplicateKeyException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private final PassengerUniquenessRule passportRule;
    private final SearchSpecification searchSpecification;
    private final MongoTemplate mongoTemplate;

    @Override
    public PassengerResponse createPassenger(PassengerRequest request) {
        passportRule.validate(request);

        Passenger passenger = passengerMapper.toEntity(request);
        try {
            Passenger savedPassenger = passengerRepository.save(passenger);
            return passengerMapper.toResponse(savedPassenger);
        } catch (DuplicateKeyException exception) {
            throw new DuplicatePassengerException(
                    String.format("Passenger with passport %s (%s) already exists",
                            request.getPassportNumber(), request.getPassportCountry()));
        }
    }

    @Override
    public PassengerResponse getPassengerById(ObjectId id) {
        return passengerMapper.toResponse(findPassengerById(id));
    }

    @Override
    public List<PassengerResponse> getAllPassengers() {
        return passengerRepository.findAll().stream().map(passengerMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public PassengerResponse updatePassenger(ObjectId id, PassengerRequest request) {
        Passenger passenger = findPassengerById(id);
        passengerMapper.updateEntityFromRequest(request, passenger);
        return passengerMapper.toResponse(passengerRepository.save(passenger));
    }

    @Override
    public void deletePassenger(ObjectId id) {
        if (!passengerRepository.existsById(id)) {
            throw new PassengerNotFoundException(
                    String.format("Passenger with id %s not found!", id));
        }
        passengerRepository.deleteById(id);
    }

    @Override
    public List<PassengerResponse> search(PassengerSearchRequest request) {
        Query query = searchSpecification.toQuery(request);
        List<Passenger> passengers = mongoTemplate.find(query, Passenger.class);
        return passengerMapper.toPassengerResponses(passengers);
    }

    private Passenger findPassengerById(ObjectId id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(
                        String.format("Passenger with given id %s not found!", id)));
    }
}