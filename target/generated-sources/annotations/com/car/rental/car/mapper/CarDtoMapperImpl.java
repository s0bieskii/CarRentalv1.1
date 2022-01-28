package com.car.rental.car.mapper;

import com.car.rental.car.Car;
import com.car.rental.car.dto.CarDto;
import com.car.rental.car.dto.CarUpdateDto;
import com.car.rental.details.CarDetails;
import com.car.rental.details.dto.CarDetailsUpdateDto;
import com.car.rental.details.mapper.CarDetailsDtoMapper;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-28T11:24:05+0100",
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

    @Override
    public Car carUpdateToCar(CarUpdateDto carUpdateDto) {
        if ( carUpdateDto == null ) {
            return null;
        }

        Car car = new Car();

        car.setId( carUpdateDto.getId() );
        car.setBrand( carUpdateDto.getBrand() );
        car.setModel( carUpdateDto.getModel() );
        if ( carUpdateDto.getAvailable() != null ) {
            car.setAvailable( carUpdateDto.getAvailable() );
        }
        if ( carUpdateDto.getDeleted() != null ) {
            car.setDeleted( carUpdateDto.getDeleted() );
        }
        car.setCarDetails( carDetailsUpdateDtoToCarDetails( carUpdateDto.getCarDetails() ) );

        return car;
    }

    protected CarDetails carDetailsUpdateDtoToCarDetails(CarDetailsUpdateDto carDetailsUpdateDto) {
        if ( carDetailsUpdateDto == null ) {
            return null;
        }

        CarDetails carDetails = new CarDetails();

        carDetails.setId( carDetailsUpdateDto.getId() );
        carDetails.setVin( carDetailsUpdateDto.getVin() );
        carDetails.setColor( carDetailsUpdateDto.getColor() );
        carDetails.setRegistrationYear( carDetailsUpdateDto.getRegistrationYear() );
        carDetails.setPrice( carDetailsUpdateDto.getPrice() );
        carDetails.setSegment( carDetailsUpdateDto.getSegment() );
        carDetails.setDoors( carDetailsUpdateDto.getDoors() );
        carDetails.setSeats( carDetailsUpdateDto.getSeats() );
        carDetails.setRegistrationNumber( carDetailsUpdateDto.getRegistrationNumber() );
        if ( carDetailsUpdateDto.getMileage() != null ) {
            carDetails.setMileage( carDetailsUpdateDto.getMileage() );
        }
        carDetails.setInspection( carDetailsUpdateDto.getInspection() );
        carDetails.setInsurance( carDetailsUpdateDto.getInsurance() );
        carDetails.setFuel( carDetailsUpdateDto.getFuel() );
        if ( carDetailsUpdateDto.getAverageConsumption() != null ) {
            carDetails.setAverageConsumption( carDetailsUpdateDto.getAverageConsumption() );
        }
        carDetails.setTransmission( carDetailsUpdateDto.getTransmission() );

        return carDetails;
    }
}
