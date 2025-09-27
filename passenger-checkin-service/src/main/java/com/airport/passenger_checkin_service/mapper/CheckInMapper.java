package com.airport.passenger_checkin_service.mapper;

import com.airport.passenger_checkin_service.domain.entity.FlightCheckInRecord;
import com.airport.passenger_checkin_service.domain.dto.request.FlightCheckInRequest;
import com.airport.passenger_checkin_service.domain.dto.response.FlightCheckInResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CheckInMapper {

    FlightCheckInRecord toEntity(FlightCheckInRequest request);

    @Mapping(target = "id", expression = "java(checkIn.getId() != null ? checkIn.getId().toHexString() : null)")
    FlightCheckInResponse toResponse(FlightCheckInRecord checkIn);

    List<FlightCheckInResponse> toResponses(List<FlightCheckInRecord> entities);

}