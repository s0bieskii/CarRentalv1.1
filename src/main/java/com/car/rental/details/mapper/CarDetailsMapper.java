package com.car.rental.details.mapper;

import com.car.rental.details.CarDetails;
import com.car.rental.details.dto.CarDetailsAddDto;
import com.car.rental.details.dto.CarDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarDetailsMapper {
    CarDetailsMapper CAR_DETAILS_DTO_MAPPER = Mappers.getMapper(CarDetailsMapper.class);

    CarDetailsDto toCarDto(CarDetails carDetails);

    CarDetails carAddDetailsDtoToCarDetails(CarDetailsAddDto carAddDto);
}
