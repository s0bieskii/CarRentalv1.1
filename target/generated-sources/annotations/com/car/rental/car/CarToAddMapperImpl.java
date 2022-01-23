package com.car.rental.car;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-23T10:43:44+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class CarToAddMapperImpl implements CarAddMapper {

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

        return car;
    }
}
