package com.car.rental.details.repository;

import com.car.rental.details.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetails, Integer> {
}
