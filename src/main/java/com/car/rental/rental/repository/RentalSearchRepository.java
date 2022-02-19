package com.car.rental.rental.repository;

import com.car.rental.rental.Rental;
import com.car.rental.rental.dto.RentalSearchDto;
import java.util.List;

public interface RentalSearchRepository {

    List<Rental> find(RentalSearchDto rentalSearchDto);
}
