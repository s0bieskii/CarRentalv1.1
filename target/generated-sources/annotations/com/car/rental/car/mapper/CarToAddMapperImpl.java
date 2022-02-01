package com.car.rental.car.mapper;

import com.car.rental.car.Car;
import com.car.rental.car.dto.CarAddDto;
import com.car.rental.details.mapper.CarDetailsAddDtoMapper;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-28T14:21:34+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class CarToAddMapperImpl implements CarToAddMapper {

    @Autowired
    private CarDetailsAddDtoMapper carDetailsAddDtoMapper;

    @Override
    public Car toCar(CarAddDto carAddDto) {
        if ( carAddDto == null ) {
            return null;
        }

        Car car = new Car();

        car.setBrand( carAddDto.getBrand() );
        car.setModel( carAddDto.getModel() );
        car.setAvailable( carAddDto.isAvailable() );
        car.setDeleted( carAddDto.isDeleted() );
        car.setCarDetails( carDetailsAddDtoMapper.toCarDetails( carAddDto.getCarDetails() ) );

        return car;
    }
}
