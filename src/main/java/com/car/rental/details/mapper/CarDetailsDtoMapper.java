package com.car.rental.details.mapper;

import com.car.rental.details.CarDetails;
import com.car.rental.details.dto.CarDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarDetailsDtoMapper {
    CarDetailsDtoMapper CAR_DETAILS_DTO_MAPPER = Mappers.getMapper(CarDetailsDtoMapper.class);

    CarDetailsDto toCarDto(CarDetails carDetails);
}
