package com.car.rental.car;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarAddMapper {
    CarAddDto CAR_ADD_MAPPER = Mappers.getMapper(CarAddDto.class);

    Car toCar(CarAddDto carAddDto);


}
