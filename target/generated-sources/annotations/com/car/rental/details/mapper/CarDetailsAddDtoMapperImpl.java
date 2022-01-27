package com.car.rental.details.mapper;

import com.car.rental.details.CarDetails;
import com.car.rental.details.dto.CarDetailsAddDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-27T20:31:11+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class CarDetailsAddDtoMapperImpl implements CarDetailsAddDtoMapper {

    @Override
    public CarDetails toCarDetails(CarDetailsAddDto carAddDto) {
        if ( carAddDto == null ) {
            return null;
        }

        CarDetails carDetails = new CarDetails();

        carDetails.setVin( carAddDto.getVin() );
        carDetails.setColor( carAddDto.getColor() );
        carDetails.setRegistrationYear( carAddDto.getRegistrationYear() );
        carDetails.setPrice( carAddDto.getPrice() );
        carDetails.setSegment( carAddDto.getSegment() );
        carDetails.setDoors( carAddDto.getDoors() );
        carDetails.setSeats( carAddDto.getSeats() );
        carDetails.setRegistrationNumber( carAddDto.getRegistrationNumber() );
        carDetails.setMileage( carAddDto.getMileage() );
        carDetails.setInspection( carAddDto.getInspection() );
        carDetails.setInsurance( carAddDto.getInsurance() );
        carDetails.setFuel( carAddDto.getFuel() );
        carDetails.setAverageConsumption( carAddDto.getAverageConsumption() );
        carDetails.setTransmission( carAddDto.getTransmission() );

        return carDetails;
    }
}
