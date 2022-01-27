package com.car.rental.car.repository;

import com.car.rental.car.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer>, CarSearchRepository{

    Page<Car> findAll(Pageable pageable);
}
