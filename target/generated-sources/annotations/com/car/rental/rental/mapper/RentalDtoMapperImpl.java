package com.car.rental.rental.mapper;

import com.car.rental.employee.Employee;
import com.car.rental.employee.dto.EmployeeDto;
import com.car.rental.employee.mapper.EmployeeDtoMapper;
import com.car.rental.rental.Rental;
import com.car.rental.rental.dto.RentalDto;
import com.car.rental.rental.dto.RentalWithoutEmployeeDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-28T14:21:33+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class RentalDtoMapperImpl implements RentalDtoMapper {

    @Autowired
    private EmployeeDtoMapper employeeDtoMapper;

    @Override
    public RentalDto rentalToRentalDto(Rental rental) {
        if ( rental == null ) {
            return null;
        }

        RentalDto rentalDto = new RentalDto();

        rentalDto.setId( rental.getId() );
        rentalDto.setCountry( rental.getCountry() );
        rentalDto.setCity( rental.getCity() );
        rentalDto.setPostCode( rental.getPostCode() );
        rentalDto.setStreet( rental.getStreet() );
        rentalDto.setPhone( rental.getPhone() );
        rentalDto.setDeleted( rental.isDeleted() );
        rentalDto.setEmployees( employeeListToEmployeeDtoList( rental.getEmployees() ) );

        return rentalDto;
    }

    @Override
    public RentalWithoutEmployeeDto rentalToRentalWithoutEmployeeDto(Rental rental) {
        if ( rental == null ) {
            return null;
        }

        RentalWithoutEmployeeDto rentalWithoutEmployeeDto = new RentalWithoutEmployeeDto();

        rentalWithoutEmployeeDto.setId( rental.getId() );
        rentalWithoutEmployeeDto.setCountry( rental.getCountry() );
        rentalWithoutEmployeeDto.setCity( rental.getCity() );
        rentalWithoutEmployeeDto.setPostCode( rental.getPostCode() );
        rentalWithoutEmployeeDto.setStreet( rental.getStreet() );
        rentalWithoutEmployeeDto.setPhone( rental.getPhone() );
        rentalWithoutEmployeeDto.setDeleted( rental.isDeleted() );

        return rentalWithoutEmployeeDto;
    }

    protected List<EmployeeDto> employeeListToEmployeeDtoList(List<Employee> list) {
        if ( list == null ) {
            return null;
        }

        List<EmployeeDto> list1 = new ArrayList<EmployeeDto>( list.size() );
        for ( Employee employee : list ) {
            list1.add( employeeDtoMapper.employeeToEmployeeDto( employee ) );
        }

        return list1;
    }
}
