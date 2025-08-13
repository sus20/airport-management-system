package com.airport.flightscheduler.service.implementation;

import com.airport.flightscheduler.domain.Flight;
import com.airport.flightscheduler.dto.FlightDTO;
import com.airport.flightscheduler.exception.FlightNotFoundException;
import com.airport.flightscheduler.mapper.DTOMapper;
import com.airport.flightscheduler.repository.FlightRepository;
import com.airport.flightscheduler.service.FlightService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepo;
    private final DTOMapper dtoMapper;

    @Override
    public FlightDTO createFlight(Flight flight) {
        Flight savedFlight = flightRepo.save(flight);
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
        return dtoMapper.toFlightDTO(savedFlight);
    }

    @Override
    public void deleteFlight(ObjectId id) {
        findFlightOrThrow(id);
        flightRepo.deleteById(id);
    }

    private Flight findFlightOrThrow(ObjectId id) {
        return flightRepo.findById(id).orElseThrow(() -> new FlightNotFoundException(id));
    }
}
