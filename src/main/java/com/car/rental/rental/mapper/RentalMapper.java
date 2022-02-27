package com.car.rental.rental.mapper;

import com.car.rental.details.mapper.CarDetailsMapper;
import com.car.rental.employee.mapper.EmployeeMapper;
import com.car.rental.rental.Rental;
import com.car.rental.rental.dto.RentalAddDto;
import com.car.rental.rental.dto.RentalDto;
import com.car.rental.rental.dto.RentalUpdateDto;
import com.car.rental.rental.dto.RentalWithoutEmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CarDetailsMapper.class, EmployeeMapper.class})
public interface RentalMapper {
    RentalMapper CAR_DTO_MAPPER = Mappers.getMapper(RentalMapper.class);

    RentalDto rentalToRentalDto(Rental rental);
    Rental rentalAddDtoToRental(RentalAddDto rentalAddDto);
    RentalWithoutEmployeeDto rentalToRentalWithoutEmployeeDto(Rental rental);
    Rental rentalUpdateDtoToRental(RentalUpdateDto rentalUpdateDto);
}
