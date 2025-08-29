package com.airport.flightscheduler.service.implementation;

import com.airport.flightscheduler.domain.Flight;
import com.airport.flightscheduler.domain.FlightEvent;
import com.airport.flightscheduler.dto.FlightSearchRequest;
import com.airport.flightscheduler.dto.FlightDTO;
import com.airport.flightscheduler.exception.FlightNotFoundException;
import com.airport.flightscheduler.mapper.DTOMapper;
import com.airport.flightscheduler.repository.FlightRepository;
import com.airport.flightscheduler.service.FlightEventProducer;
import com.airport.flightscheduler.service.FlightService;
import com.airport.flightscheduler.search.FlightSearchSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepo;
    private final FlightSearchSpecification flightSearchSpecification;
    private final DTOMapper dtoMapper;
    private final MongoTemplate mongoTemplate;
    private final FlightEventProducer eventProducer;

    @Override
    public FlightDTO createFlight(Flight flight) {
        Flight savedFlight = flightRepo.save(flight);
        log.info("Flight saved: {}", savedFlight);

        eventProducer.sendFlightEvent(
                FlightEvent.builder()
                        .eventType("CREATED")
                        .flight(savedFlight)
                        .timestamp(Instant.now())
                        .build()
        );
        return dtoMapper.toFlightDTO(savedFlight);
    }

    @Override
    public List<FlightDTO> getAllFlights(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Flight> flights = flightRepo.findAll(pageable);
        return dtoMapper.toFlightDTOs(flights.getContent());
    }

    @Override
    public FlightDTO getFlightById(ObjectId id) {
        return dtoMapper.toFlightDTO(findFlightOrThrow(id));
    }

    @Override
    public FlightDTO updateFlight(ObjectId id, Flight incoming) {
        findFlightOrThrow(id);
        incoming.setId(id);
        Flight savedFlight = flightRepo.save(incoming);
        eventProducer.sendFlightEvent(
                FlightEvent.builder()
                        .eventType("UPDATED")
                        .flight(savedFlight)
                        .timestamp(Instant.now())
                        .build()
        );
        return dtoMapper.toFlightDTO(savedFlight);
    }

    @Override
    public void deleteFlight(ObjectId id) {
        Flight existing = findFlightOrThrow(id);
        flightRepo.deleteById(id);
        eventProducer.sendFlightEvent(
                FlightEvent.builder()
                        .eventType("DELETED")
                        .flight(existing)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @Override
    public List<FlightDTO> search(FlightSearchRequest request) {
        Query query = flightSearchSpecification.toQuery(request);
        List<Flight> result = mongoTemplate.find(query, Flight.class);
        return dtoMapper.toFlightDTOs(result);
    }

    private Flight findFlightOrThrow(ObjectId id) {
        return flightRepo.findById(id).orElseThrow(() -> new FlightNotFoundException(id));
    }
}
