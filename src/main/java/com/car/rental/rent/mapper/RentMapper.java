package com.car.rental.rent.mapper;

import com.car.rental.car.mapper.CarMapper;
import com.car.rental.rent.Rent;
import com.car.rental.rent.dto.RentAddDto;
import com.car.rental.rent.dto.RentAdminViewDto;
import com.car.rental.rent.dto.RentDto;
import com.car.rental.rent.dto.RentUpdateDto;
import com.car.rental.report.mapper.ReturnReportMapper;
import com.car.rental.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, CarMapper.class, ReturnReportMapper.class})
public interface RentMapper {
    RentMapper RENT_DTO_MAPPER = Mappers.getMapper(RentMapper.class);

    RentDto rentToRentDto(Rent rent);

    Rent rentAddDtoToRent(RentAddDto rentAddDto);

    Rent rentUpdateDtoToEmployee(RentUpdateDto rentUpdateDto);

    RentAdminViewDto rentToRentAdminViewDto(Rent rent);

}
