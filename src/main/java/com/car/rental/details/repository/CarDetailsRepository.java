package com.car.rental.details.repository;

import com.car.rental.details.CarDetails;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetails, Integer> {
    @Query(value = "SELECT distinct color FROM car_details WHERE color IS NOT NULL", nativeQuery = true)
    List<String> findAllCarDetailsColor();

    @Query(value = "SELECT distinct fuel FROM car_details WHERE fuel IS NOT NULL", nativeQuery = true)
    List<String> findAllCarsFuels();

    @Query(value = "SELECT distinct seats FROM car_details WHERE seats IS NOT NULL", nativeQuery = true)
    List<String> findCarsSeats();

    @Query(value = "SELECT distinct segment FROM car_details WHERE segment IS NOT NULL", nativeQuery = true)
    List<String> findAllCarsSegments();

    @Query(value = "SELECT distinct doors FROM car_details WHERE doors IS NOT NULL", nativeQuery = true)
    List<String> getDoorsQuantity();

    @Query(value = "SELECT MAX(price) FROM car_details", nativeQuery = true)
    BigDecimal getMaxPrice();
}
