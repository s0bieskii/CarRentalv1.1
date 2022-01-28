package com.car.rental.details.mapper;

import com.car.rental.details.CarDetails;
import com.car.rental.details.dto.CarDetailsDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-28T11:24:05+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class CarDetailsDtoMapperImpl implements CarDetailsDtoMapper {

    @Override
    public CarDetailsDto toCarDto(CarDetails carDetails) {
        if ( carDetails == null ) {
            return null;
        }

        CarDetailsDto carDetailsDto = new CarDetailsDto();

        carDetailsDto.setId( carDetails.getId() );
        carDetailsDto.setColor( carDetails.getColor() );
        carDetailsDto.setRegistrationYear( carDetails.getRegistrationYear() );
        carDetailsDto.setPrice( carDetails.getPrice() );
        carDetailsDto.setSegment( carDetails.getSegment() );
        carDetailsDto.setDoors( carDetails.getDoors() );
        carDetailsDto.setSeats( carDetails.getSeats() );
        carDetailsDto.setFuel( carDetails.getFuel() );
        carDetailsDto.setAverageConsumption( carDetails.getAverageConsumption() );
        carDetailsDto.setTransmission( carDetails.getTransmission() );

        return carDetailsDto;
    }
}
