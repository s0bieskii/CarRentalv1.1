package com.car.rental.details.mapper;

import com.car.rental.details.CarDetails;
import com.car.rental.details.dto.CarDetailsAddDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarDetailsAddDtoMapper {
    CarDetailsAddDto CAR_DETAILS_ADD_MAPPER = Mappers.getMapper(CarDetailsAddDto.class);

    CarDetails toCarDetails(CarDetailsAddDto carAddDto);

}
