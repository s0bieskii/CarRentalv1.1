package com.car.rental.car.repository;

import com.car.rental.car.Car;
import com.car.rental.car.dto.CarSearchDto;

import java.util.List;


public interface CarSearchRepository{

    public List<Car> find(CarSearchDto carDto);
}
