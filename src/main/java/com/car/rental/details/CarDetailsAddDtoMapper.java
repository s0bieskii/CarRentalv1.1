package com.car.rental.details;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarDetailsAddDtoMapper {
    CarDetailsAddDto CAR_DETAILS_ADD_MAPPER = Mappers.getMapper(CarDetailsAddDto.class);

    CarDetails toCarDetails(CarDetailsAddDto carAddDto);

}
