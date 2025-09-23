package com.airport.passenger_checkin_service.mapper;

import com.airport.passenger_checkin_service.domain.CheckIn;
import com.airport.passenger_checkin_service.dto.CheckInRequest;
import com.airport.passenger_checkin_service.dto.CheckInResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CheckInMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "checkInTime", ignore = true)
    @Mapping(target = "boardingPassUrl", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CheckIn toEntity(CheckInRequest request);

    @Mapping(target = "id", expression = "java(checkIn.getId() != null ? checkIn.getId().toHexString() : null)")
    CheckInResponse toResponse(CheckIn checkIn);

    List<CheckInResponse> toResponses(List<CheckIn> entities);
}