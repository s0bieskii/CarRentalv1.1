package com.car.rental.rental.mapper;

import com.car.rental.details.mapper.CarDetailsDtoMapper;
import com.car.rental.employee.mapper.EmployeeMapper;
import com.car.rental.rental.Rental;
import com.car.rental.rental.dto.RentalDto;
import com.car.rental.rental.dto.RentalWithoutEmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CarDetailsDtoMapper.class, EmployeeMapper.class})
public interface RentalDtoMapper {
    RentalDtoMapper CAR_DTO_MAPPER = Mappers.getMapper(RentalDtoMapper.class);

    RentalDto rentalToRentalDto(Rental rental);
    RentalWithoutEmployeeDto rentalToRentalWithoutEmployeeDto(Rental rental);
}
