package com.car.rental.rent.repository;

import com.car.rental.rent.Rent;
import com.car.rental.rent.dto.RentSearchDto;
import java.util.List;

public interface RentSearchRepository {

    List<Rent> find(RentSearchDto rentSearchDto);
}
