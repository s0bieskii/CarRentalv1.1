package com.car.rental.car.mapper;

import com.car.rental.car.Car;
import com.car.rental.car.dto.CarDto;
import com.car.rental.details.mapper.CarDetailsDtoMapper;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-25T13:38:45+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class CarDtoMapperImpl implements CarDtoMapper {

    @Autowired
    private CarDetailsDtoMapper carDetailsDtoMapper;

    @Override
    public CarDto carToCarDto(Car car) {
        if ( car == null ) {
            return null;
        }

        CarDto carDto = new CarDto();

        carDto.setId( car.getId() );
        carDto.setBrand( car.getBrand() );
        carDto.setModel( car.getModel() );
        carDto.setAvailable( car.isAvailable() );
        carDto.setDeleted( car.isDeleted() );
        carDto.setCarDetails( carDetailsDtoMapper.toCarDto( car.getCarDetails() ) );

        return carDto;
    }
}
