package com.car.rental.car.mapper;

import com.car.rental.car.Car;
import com.car.rental.car.dto.CarAddDto;
import com.car.rental.car.dto.CarDto;
import com.car.rental.car.dto.CarUpdateDto;
import com.car.rental.details.mapper.CarDetailsDtoMapper;
import com.car.rental.rental.mapper.RentalDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CarDetailsDtoMapper.class, RentalDtoMapper.class})
public interface CarDtoMapper {
    CarDtoMapper CAR_DTO_MAPPER = Mappers.getMapper(CarDtoMapper.class);

    CarDto carToCarDto(Car car);
    Car carUpdateToCar(CarUpdateDto carUpdateDto);
    Car carAddToCar(CarAddDto carAddDto);
}
