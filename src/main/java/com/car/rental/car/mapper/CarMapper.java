package com.car.rental.car.mapper;

import com.car.rental.car.Car;
import com.car.rental.car.dto.CarAddDto;
import com.car.rental.car.dto.CarDto;
import com.car.rental.car.dto.CarUpdateDto;
import com.car.rental.details.mapper.CarDetailsMapper;
import com.car.rental.rental.mapper.RentalMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CarDetailsMapper.class, RentalMapper.class})
public interface CarMapper {
    CarMapper CAR_DTO_MAPPER = Mappers.getMapper(CarMapper.class);

    CarDto carToCarDto(Car car);
    Car carUpdateToCar(CarUpdateDto carUpdateDto);
    Car carAddToCar(CarAddDto carAddDto);
}
