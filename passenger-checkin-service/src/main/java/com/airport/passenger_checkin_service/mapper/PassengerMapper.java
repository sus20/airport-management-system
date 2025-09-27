package com.airport.passenger_checkin_service.mapper;

import com.airport.passenger_checkin_service.domain.entity.Passenger;
import com.airport.passenger_checkin_service.domain.dto.request.PassengerRequest;
import com.airport.passenger_checkin_service.domain.dto.response.PassengerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Passenger toEntity(PassengerRequest request);

    @Mapping(target = "id", expression = "java(passenger.getId() != null ? passenger.getId().toHexString() : null)")
    PassengerResponse toResponse(Passenger passenger);
    List<PassengerResponse> toPassengerResponses(List<Passenger> passengers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(PassengerRequest request, @MappingTarget Passenger passenger);
}