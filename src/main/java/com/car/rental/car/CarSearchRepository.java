package com.car.rental.car;

import com.car.rental.car.dto.CarSearchDto;

import java.util.List;


public interface CarSearchRepository{

    public List<Car> find(CarSearchDto carDto);
}
