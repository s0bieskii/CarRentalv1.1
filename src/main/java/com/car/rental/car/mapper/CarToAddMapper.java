package com.car.rental.car.mapper;

import com.car.rental.car.Car;
import com.car.rental.car.dto.CarAddDto;
import com.car.rental.details.mapper.CarDetailsAddDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper (uses = CarDetailsAddDtoMapper.class)
public interface CarToAddMapper {
    CarAddDto CAR_ADD_MAPPER = Mappers.getMapper(CarAddDto.class);


    Car toCar(CarAddDto carAddDto);


}